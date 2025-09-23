package com.prysma.mapper;

import com.prysma.dto.usuario.UsuarioDTO;
import com.prysma.dto.usuario.UsuarioCreateDTO;
import com.prysma.model.usuario.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.isAdmin(),
                usuario.isAtivo(),
                usuario.getDataCriacao(),
                usuario.getDataAtualizacao()
        );
    }

    public static Usuario toEntity(UsuarioCreateDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), dto.getSenha());
        usuario.setAdmin(false); // regra: sempre começa como não admin
        usuario.setAtivo(true);
        return usuario;
    }
}