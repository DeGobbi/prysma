package com.prysma.dto.produto;

import com.prysma.model.produto.Tamanho;

public class ProdutoEstoqueDTO {
    private Long produtoId;
    private Long corId;
    private Long tamanhoId;
    private Integer quantidade;

    // Getters e Setters
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public Long getCorId() { return corId; }
    public void setCorId(Long corId) { this.corId = corId; }

    public Long getTamanhoId() { return tamanhoId; }
    public void setTamanhoId(Long tamanhoId) { this.tamanhoId = tamanhoId; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}