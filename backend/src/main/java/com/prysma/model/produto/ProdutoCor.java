package com.prysma.model.produto;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produto_cores")
public class ProdutoCor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.PERSIST) // 👈 permite criar a Cor junto
    @JoinColumn(name = "cor_id")
    private Cor cor;

    @OneToMany(mappedBy = "produtoCor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoImagem> imagens;

    @OneToMany(mappedBy = "produtoCor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoEstoque> estoques;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Cor getCor() { return cor; }
    public void setCor(Cor cor) { this.cor = cor; }

    public List<ProdutoImagem> getImagens() { return imagens; }
    public void setImagens(List<ProdutoImagem> imagens) { this.imagens = imagens; }

    public List<ProdutoEstoque> getEstoques() { return estoques; }
    public void setEstoques(List<ProdutoEstoque> estoques) { this.estoques = estoques; }

}