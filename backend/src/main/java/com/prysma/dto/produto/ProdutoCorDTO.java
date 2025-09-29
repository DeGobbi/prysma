package com.prysma.dto.produto;

import java.util.List;

public class ProdutoCorDTO {
    private String corNome; // agora a cor será criada a partir do nome
    private List<String> imagens;
    private List<ProdutoEstoqueDTO> estoques;

    // Getters e Setters
    public String getCorNome() { return corNome; }
    public void setCorNome(String corNome) { this.corNome = corNome; }

    public List<String> getImagens() { return imagens; }
    public void setImagens(List<String> imagens) { this.imagens = imagens; }

    public List<ProdutoEstoqueDTO> getEstoques() { return estoques; }
    public void setEstoques(List<ProdutoEstoqueDTO> estoques) { this.estoques = estoques; }
}