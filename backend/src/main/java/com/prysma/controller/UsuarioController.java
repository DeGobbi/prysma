package com.prysma.controller;

import com.prysma.model.Usuario;
import com.prysma.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // POST /usuarios → cria novo usuário
    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // PUT /usuarios/{id} → atualiza usuário
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNome(usuario.getNome());
            existente.setEmail(usuario.getEmail());
            return usuarioRepository.save(existente);
        }
        return null;
    }

    // DELETE /usuarios/{id} → deleta usuário
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}