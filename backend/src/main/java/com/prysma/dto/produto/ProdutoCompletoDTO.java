package com.prysma.dto.produto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoCompletoDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Long generoId; // agora é ID
    private List<Long> categoriaIds; // agora são IDs
    private List<ProdutoCorDTO> cores;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Long getGeneroId() { return generoId; }
    public void setGeneroId(Long generoId) { this.generoId = generoId; }

    public List<Long> getCategoriaIds() { return categoriaIds; }
    public void setCategoriaIds(List<Long> categoriaIds) { this.categoriaIds = categoriaIds; }

    public List<ProdutoCorDTO> getCores() { return cores; }
    public void setCores(List<ProdutoCorDTO> cores) { this.cores = cores; }
}