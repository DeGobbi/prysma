package com.prysma.mapper;

import com.prysma.dto.produto.ProdutoDTO;
import com.prysma.model.produto.Categoria;
import com.prysma.model.produto.Genero;
import com.prysma.model.produto.Produto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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