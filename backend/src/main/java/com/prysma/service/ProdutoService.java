package com.prysma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prysma.controller.ImagemController;
import com.prysma.dto.produto.ProdutoDTO;
import com.prysma.dto.produto.ProdutoEstoqueDTO;
import com.prysma.dto.produto.ProdutoCompletoDTO;
import com.prysma.mapper.ProdutoMapper;
import com.prysma.model.produto.*;
import com.prysma.repository.produto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import com.prysma.service.ImagemService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;




@Service
public class ProdutoService {

    private static final Logger log = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private ProdutoEstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoImagemRepository imagemRepository;

    @Autowired
    private TamanhoRepository tamanhoRepository;

    @Autowired
    private CorRepository corRepository;

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private ImagemController imagemController;



    public ProdutoDTO criarProduto(ProdutoDTO dto) {
        // busca categorias pelo nome
        List<Categoria> categorias = categoriaRepository.findByNomeIn(dto.getCategorias());

        // busca genero
        Genero genero = generoRepository.findByNome(dto.getGenero())
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado"));

        Produto produto = ProdutoMapper.toEntity(dto, categorias, genero);
        produto.setDesconto(null);
        produto = produtoRepository.save(produto);

        return ProdutoMapper.toDTO(produto);
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProdutoMapper.toDTO(produto);
    }

    // Adicionar estoque
    public ProdutoEstoque adicionarEstoque(ProdutoEstoqueDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProdutoCor cor = produto.getCores().stream()
                .filter(c -> c.getId().equals(dto.getCorId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cor não encontrada"));

        ProdutoEstoque estoque = new ProdutoEstoque();
        estoque.setProdutoCor(cor);
        Tamanho tamanho = tamanhoRepository.findById(dto.getTamanhoId())
                .orElseThrow(() -> new RuntimeException("Tamanho não encontrado"));
        estoque.setTamanho(tamanho);
        estoque.setQuantidade(dto.getQuantidade());

        return estoqueRepository.save(estoque);
    }

    // Adicionar imagens à cor
    public List<ProdutoImagem> adicionarImagens(Long corId, List<String> urls) {
        ProdutoCor cor = produtoRepository.findAll()
                .stream()
                .flatMap(p -> p.getCores().stream())
                .filter(c -> c.getId().equals(corId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cor não encontrada"));

        List<ProdutoImagem> imagens = urls.stream().map(url -> {
            ProdutoImagem img = new ProdutoImagem();
            img.setProdutoCor(cor);
            img.setUrlImagem(url);
            return img;
        }).collect(Collectors.toList());

        return imagemRepository.saveAll(imagens);
    }

    @Transactional
    public ProdutoDTO criarProdutoCompleto(ProdutoCompletoDTO dto) throws IOException {
        if (dto.getGeneroId() == null) {
            throw new IllegalArgumentException("O campo generoId não pode ser nulo");
        }
        if (dto.getCategoriaIds() == null || dto.getCategoriaIds().isEmpty()) {
            throw new IllegalArgumentException("É necessário informar pelo menos uma categoria");
        }
        if (dto.getCategoriaIds().stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("CategoriaIds não pode conter valores nulos");
        }
        if (dto.getReferencia() != null && !dto.getReferencia().isBlank()) {
            boolean exists = produtoRepository.existsByReferencia(dto.getReferencia());
            if (exists) {
                throw new IllegalArgumentException("A referência '" + dto.getReferencia() + "' já está em uso.");
            }
        }

        // Buscar categorias
        List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriaIds());
        if (categorias.isEmpty()) {
            throw new RuntimeException("Nenhuma categoria encontrada para os IDs fornecidos");
        }

        // Buscar gênero
        Genero genero = generoRepository.findById(dto.getGeneroId())
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado"));

        // Criar produto base
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setGenero(genero);
        produto.setCategorias(categorias);
        produto.setDesconto(null);
        produto.setReferencia(
                dto.getReferencia() != null && !dto.getReferencia().isBlank() ? dto.getReferencia() : null
        );


        System.out.println(produto.getReferencia());

        // Mapear cores
        List<ProdutoCor> cores = dto.getCores().stream().map(corDTO -> {
            ProdutoCor corEntity = new ProdutoCor();

            // Criar a entidade Cor a partir do nome
            Cor cor = new Cor();
            cor.setNome(corDTO.getCorNome());
            corEntity.setCor(cor);

            corEntity.setProduto(produto);

            // Mapear imagens
            List<ProdutoImagem> imagens = corDTO.getImagens().stream().map(url -> {
                ProdutoImagem img = new ProdutoImagem();
                img.setProdutoCor(corEntity);
                img.setUrlImagem(url);
                return img;
            }).collect(Collectors.toList());
            corEntity.setImagens(imagens);

            // Mapear estoques
            List<ProdutoEstoque> estoques = corDTO.getEstoques().stream().map(est -> {
                ProdutoEstoque estoque = new ProdutoEstoque();
                estoque.setProdutoCor(corEntity);
                estoque.setQuantidade(est.getQuantidade());

                Tamanho tamanho = tamanhoRepository.findById(est.getTamanhoId())
                        .orElseThrow(() -> new RuntimeException("Tamanho não encontrado"));
                estoque.setTamanho(tamanho);

                return estoque;
            }).collect(Collectors.toList());
            corEntity.setEstoques(estoques);

            return corEntity;
        }).collect(Collectors.toList());

        produto.setCores(cores);

        // Salvar produto (cascade salva cores, imagens, estoques e cores novas)
        Produto salvo = produtoRepository.save(produto);

        for (ProdutoCor cor : salvo.getCores()) {


            for (ProdutoImagem img : cor.getImagens()) {

                String urlTemp = img.getUrlImagem();

                String novaUrl = imagemService.moverImagemParaProduto(urlTemp, salvo.getId(), cor.getId());


                img.setUrlImagem(novaUrl);
                imagemRepository.save(img);
            }
        }
        // 🔄 Salva novamente para atualizar as URLs definitivas
        produtoRepository.save(salvo);

        imagemController.limparTemp();

        return ProdutoMapper.toDTO(salvo);
    }
}