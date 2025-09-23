package com.prysma.dto.usuario;

public class UsuarioCreateDTO {
    private String nome;
    private String email;
    private String senha;

    public UsuarioCreateDTO() {}

    public UsuarioCreateDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
}