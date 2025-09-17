package com.prysma.controller;

import com.prysma.dto.UsuarioDTO;
import com.prysma.dto.UsuarioCreateDTO;
import com.prysma.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioDTO criar(@RequestBody UsuarioCreateDTO dto) {
        return usuarioService.criarUsuario(dto);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody UsuarioCreateDTO dto) {
        return usuarioService.atualizarUsuario(id, dto);
    }
}