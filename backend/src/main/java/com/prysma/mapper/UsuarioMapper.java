package com.prysma.mapper;

import com.prysma.dto.UsuarioDTO;
import com.prysma.dto.UsuarioCreateDTO;
import com.prysma.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getDataAtualizacao(),
                usuario.getRoles()
        );
    }

    public static Usuario toEntity(UsuarioCreateDTO dto) {
        if (dto == null) return null;
        return new Usuario(
                dto.getNome(),
                dto.getEmail(),
                dto.getSenha()
        );
    }
}