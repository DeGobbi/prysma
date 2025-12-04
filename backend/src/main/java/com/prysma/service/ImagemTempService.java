package com.prysma.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImagemTempService {

    private final Path tempDir = Path.of("uploads/temp");

    // Executa todo dia às 2 da manhã
    @Scheduled(cron = "0 0 2 * * *")
    public void limparTemp() throws IOException {

        if (!Files.exists(tempDir)) return;

        Files.list(tempDir).forEach(file -> {
            try {
                long modificado = Files.getLastModifiedTime(file).toMillis();
                long limite = System.currentTimeMillis() - (24 * 60 * 60 * 1000); // 24h

                if (modificado < limite) {
                    System.out.println("🧹 Apagando arquivo temporário: " + file.getFileName());
                    Files.delete(file);
                }
            } catch (Exception e) {
                System.err.println("Erro ao tentar deletar arquivo temp: " + file);
            }
        });
    }
}
