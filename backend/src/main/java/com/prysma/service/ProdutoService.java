package com.prysma.service;

import com.prysma.dto.produto.ProdutoDTO;
import com.prysma.mapper.ProdutoMapper;
import com.prysma.model.produto.Categoria;
import com.prysma.model.produto.Genero;
import com.prysma.model.produto.Produto;
import com.prysma.repository.produto.CategoriaRepository;
import com.prysma.repository.produto.GeneroRepository;
import com.prysma.repository.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    public ProdutoDTO criarProduto(ProdutoDTO dto) {
        // busca categorias pelo nome
        List<Categoria> categorias = categoriaRepository.findByNomeIn(dto.getCategorias());

        // busca genero
        Genero genero = generoRepository.findByNome(dto.getGenero())
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado"));

        Produto produto = ProdutoMapper.toEntity(dto, categorias, genero);
        produto = produtoRepository.save(produto);

        return ProdutoMapper.toDTO(produto);
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProdutoMapper.toDTO(produto);
    }
}