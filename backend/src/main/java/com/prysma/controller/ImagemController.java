package com.prysma.controller;

import com.prysma.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/imagens")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImagens(@RequestParam("files") MultipartFile[] files) {
        try {
            List<String> urls = Arrays.stream(files)
                    .map(file -> {
                        try {
                            return imagemService.salvarImagem(file);
                        } catch (IOException e) {
                            throw new RuntimeException("Erro ao salvar imagem: " + file.getOriginalFilename(), e);
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(urls);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer upload: " + e.getMessage());
        }
    }

    @PostMapping("/upload/temp")
    public ResponseEntity<?> uploadTemp(@RequestParam("files") MultipartFile[] files) {
        try {
            List<String> urls = Arrays.stream(files)
                    .map(file -> {
                        try {
                            return imagemService.salvarImagemTemporaria(file);
                        } catch (IOException e) {
                            throw new RuntimeException("Erro ao salvar imagem temporária: " + file.getOriginalFilename(), e);
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(urls);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro no upload temporário: " + e.getMessage());
        }
    }

    @DeleteMapping("/upload/temp/clear")
    public ResponseEntity<?> limparTemp() {
        try {
            Path tempDir = Paths.get("uploads/temp/");
            if (Files.exists(tempDir)) {
                Files.walk(tempDir)
                        .sorted((a, b) -> b.compareTo(a)) // apaga subpastas primeiro
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
            return ResponseEntity.ok("Pasta temporária limpa.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao limpar pasta temporária: " + e.getMessage());
        }
    }
}
