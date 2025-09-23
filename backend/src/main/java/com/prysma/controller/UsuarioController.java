package com.prysma.controller;

import com.prysma.dto.usuario.LoginDTO;
import com.prysma.dto.usuario.LoginResponseDTO;
import com.prysma.dto.usuario.UsuarioCreateDTO;
import com.prysma.dto.usuario.UsuarioDTO;
import com.prysma.service.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponse = usuarioService.autenticar(loginDTO);

        // cria cookie com o token (HttpOnly). secure=false em dev; em produção usar true + HTTPS.
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", loginResponse.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60) // 1 dia
                .sameSite("Lax")
                .build();

        UsuarioDTO usuarioDTO = loginResponse.getUsuario();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(usuarioDTO);
    }

    @GetMapping("/me")
    public UsuarioDTO me(@CookieValue(name = "jwt", required = false) String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Usuário não autenticado");
        }
        return usuarioService.getMe(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }
}