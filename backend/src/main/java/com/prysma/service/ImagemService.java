package com.prysma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;

@Service
public class ImagemService {

    private static final Logger log = LoggerFactory.getLogger(ImagemService.class);

    @Value("${upload.dir:uploads/produtos/}")
    private String uploadDir;

    @Value("${upload.temp.dir:uploads/temp/}")
    private String uploadTempDir;

    public String salvarImagem(MultipartFile file) throws IOException {
        // Cria diretório, se não existir
        File diretorio = new File(uploadDir);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Extrai extensão do arquivo
        String extensao = "";
        String original = file.getOriginalFilename();
        int index = original.lastIndexOf('.');
        if (index > 0) {
            extensao = original.substring(index);
        }

        // Nome único com timestamp e UUID
        String nomeArquivo = System.currentTimeMillis() + "_" + UUID.randomUUID() + extensao;

        // Caminho completo
        Path caminho = Paths.get(uploadDir + nomeArquivo);

        // Salva o arquivo físico
        Files.write(caminho, file.getBytes());

        // Retorna a URL pública (ajuste conforme a rota pública do servidor)
        return "/uploads/produtos/" + nomeArquivo;
    }

    public String salvarImagemTemporaria(MultipartFile file) throws IOException {
        File diretorio = new File(uploadTempDir);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String extensao = "";
        String original = file.getOriginalFilename();
        int index = original.lastIndexOf('.');
        if (index > 0) extensao = original.substring(index);

        String nomeArquivo = System.currentTimeMillis() + "_" + UUID.randomUUID() + extensao;

        Path caminho = Paths.get(uploadTempDir + nomeArquivo);
        Files.write(caminho, file.getBytes());

        return "http://localhost:8080/uploads/temp/" + nomeArquivo;
    }

    public String moverImagemParaProduto(String caminhoTemp, Long produtoId, Long corId) throws IOException {
        Path origem = Paths.get(caminhoTemp.replaceFirst("^/", "")); // remove / inicial

        String destinoDir = String.format("uploads/produtos/%d/%d/", produtoId, corId);
        Files.createDirectories(Paths.get(destinoDir));

        Path destino = Paths.get(destinoDir, origem.getFileName().toString());
        Files.move(origem, destino, StandardCopyOption.REPLACE_EXISTING);

        return "/" + destino.toString().replace("\\", "/");
    }
}