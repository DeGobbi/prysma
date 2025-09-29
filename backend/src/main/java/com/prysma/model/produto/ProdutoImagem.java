package com.prysma.model.produto;

import jakarta.persistence.*;

@Entity
@Table(name = "produto_cor_imagens")
public class ProdutoImagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "produto_cor_id", nullable = false)
    private ProdutoCor produtoCor;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrlImagem() { return urlImagem; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }

    public ProdutoCor getProdutoCor() { return produtoCor; }
    public void setProdutoCor(ProdutoCor produtoCor) { this.produtoCor = produtoCor; }
}