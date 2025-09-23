package com.prysma.model.produto;

import jakarta.persistence.*;

@Entity
@Table(name = "produto_estoques")
public class ProdutoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_cor_id")
    private ProdutoCor produtoCor;

    @ManyToOne
    @JoinColumn(name = "tamanho_id")
    private Tamanho tamanho;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public ProdutoCor getProdutoCor() { return produtoCor; }
    public void setProdutoCor(ProdutoCor produtoCor) { this.produtoCor = produtoCor; }

    public Tamanho getTamanho() { return tamanho; }
    public void setTamanho(Tamanho tamanho) { this.tamanho = tamanho; }
}