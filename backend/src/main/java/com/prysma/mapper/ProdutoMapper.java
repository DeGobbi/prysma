package com.prysma.mapper;

import com.prysma.dto.produto.ProdutoDTO;
import com.prysma.dto.produto.ProdutoCorDTO;
import com.prysma.dto.produto.ProdutoEstoqueDTO;
import com.prysma.model.produto.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProdutoMapper {

    public static ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setReferencia(produto.getReferencia());
        dto.setPreco(produto.getPreco());
        dto.setDesconto(produto.getDesconto());
        dto.setAtivo(produto.isAtivo());

        if (produto.getGenero() != null) {
            dto.setGenero(produto.getGenero().getNome());
        }

        if (produto.getCategorias() != null) {
            dto.setCategorias(
                    produto.getCategorias()
                            .stream()
                            .map(Categoria::getNome)
                            .toList()
            );
        }

        // 🔥 Mapear cores com imagens e estoques
        if (produto.getCores() != null) {
            List<ProdutoCorDTO> coresDTO = produto.getCores().stream().map(cor -> {
                ProdutoCorDTO corDTO = new ProdutoCorDTO();
                corDTO.setCorNome(cor.getCor().getNome());

                // Mapear imagens
                List<String> imagens = cor.getImagens() != null
                        ? cor.getImagens().stream()
                        .map(ProdutoImagem::getUrlImagem)
                        .toList()
                        : List.of();
                corDTO.setImagens(imagens);

                // Mapear estoques
                List<ProdutoEstoqueDTO> estoques = cor.getEstoques() != null
                        ? cor.getEstoques().stream().map(est -> {
                    ProdutoEstoqueDTO estDTO = new ProdutoEstoqueDTO();
                    estDTO.setQuantidade(est.getQuantidade());
                    estDTO.setTamanhoId(est.getTamanho().getId());
                    estDTO.setCorId(est.getProdutoCor().getId());
                    estDTO.setProdutoId(est.getProdutoCor().getProduto().getId());
                    return estDTO;
                }).toList()
                        : List.of();
                corDTO.setEstoques(estoques);

                return corDTO;
            }).toList();

            dto.setCores(coresDTO);
        }

        dto.setDataCriacao(produto.getDataCriacao());
        dto.setDataAtualizacao(produto.getDataAtualizacao());

        return dto;
    }

    public static Produto toEntity(ProdutoDTO dto, List<Categoria> categorias, Genero genero) {
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setReferencia(dto.getReferencia());
        produto.setPreco(dto.getPreco());
        produto.setDesconto(dto.getDesconto());
        produto.setAtivo(dto.isAtivo());

        produto.setGenero(genero);
        produto.setCategorias(categorias);

        produto.setDataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());
        return produto;
    }
}