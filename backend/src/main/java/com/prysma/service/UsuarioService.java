package com.prysma.service;

import com.prysma.dto.LoginDTO;
import com.prysma.dto.LoginResponseDTO;
import com.prysma.dto.UsuarioDTO;
import com.prysma.dto.UsuarioCreateDTO;
import com.prysma.mapper.UsuarioMapper;
import com.prysma.model.Usuario;
import com.prysma.repository.UsuarioRepository;
import com.prysma.config.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
            existente.setSenha(passwordEncoder.encode(dto.getSenha()));
            Usuario atualizado = usuarioRepository.save(existente);
            return UsuarioMapper.toDTO(atualizado);
        }
        return null;
    }

    public LoginResponseDTO autenticar(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtUtil.generateToken(usuario.getEmail());

        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);

        return new LoginResponseDTO(token, usuarioDTO);
    }

    public UsuarioDTO getMe(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token inválido ou expirado");
        }

        String email = jwtUtil.getEmailFromToken(token);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return UsuarioMapper.toDTO(usuario);
    }
}