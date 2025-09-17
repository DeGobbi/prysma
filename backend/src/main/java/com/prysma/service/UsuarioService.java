package com.prysma.service;

import com.prysma.dto.UsuarioDTO;
import com.prysma.dto.UsuarioCreateDTO;
import com.prysma.mapper.UsuarioMapper;
import com.prysma.model.Usuario;
import com.prysma.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO criarUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(salvo);
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioCreateDTO dto) {
        Usuario existente = usuarioRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNome(dto.getNome());
            existente.setEmail(dto.getEmail());
            existente.setSenha(dto.getSenha()); // depois vamos criptografar
            Usuario atualizado = usuarioRepository.save(existente);
            return UsuarioMapper.toDTO(atualizado);
        }
        return null;
    }
}