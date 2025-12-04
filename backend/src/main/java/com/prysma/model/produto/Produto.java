package com.prysma.model.produto;

import com.prysma.dto.produto.ProdutoDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo = true;

    @Column(nullable = true)
    private BigDecimal desconto = null;

    @Column(nullable = true, unique = true)
    private String referencia = null;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @ManyToMany
    @JoinTable(
            name = "produto_categorias",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoCor> cores = new ArrayList<>();

    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    // ---------- Getters e Setters ----------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public BigDecimal getDesconto() { return desconto; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }

    public Boolean isAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }

    public List<ProdutoCor> getCores() { return cores; }
    public void setCores(List<ProdutoCor> cores) { this.cores = cores; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

}