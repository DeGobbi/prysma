package com.prysma.dto.produto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoCreateDTO {
    private String nome;
    private String descricao;
    private String referencia;
    private BigDecimal preco;
    private Double desconto;
    private boolean ativo;

    private Long categoriaId;
    private Long generoId;
    private List<Long> coresIds; // ids de cores que vão ser associadas

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Double getDesconto() { return desconto; }
    public void setDesconto(Double desconto) { this.desconto = desconto; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getGeneroId() { return generoId; }
    public void setGeneroId(Long generoId) { this.generoId = generoId; }

    public List<Long> getCoresIds() { return coresIds; }
    public void setCoresIds(List<Long> coresIds) { this.coresIds = coresIds; }
}