package com.prysma.controller;

import com.prysma.dto.produto.ProdutoCompletoDTO;
import com.prysma.dto.produto.ProdutoDTO;
import com.prysma.dto.produto.ProdutoEstoqueDTO;
import com.prysma.model.produto.ProdutoEstoque;
import com.prysma.model.produto.ProdutoImagem;
import com.prysma.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Criar produto
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO dto) {
        ProdutoDTO produto = produtoService.criarProduto(dto);
        return ResponseEntity.ok(produto);
    }

    // Listar produtos
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    // Adicionar estoque a um produto
    @PostMapping("/{id}/estoque")
    public ResponseEntity<ProdutoEstoque> adicionarEstoque(
            @PathVariable Long id,
            @RequestBody ProdutoEstoqueDTO dto) {
        dto.setProdutoId(id); // garante que o produto vem do path
        ProdutoEstoque estoque = produtoService.adicionarEstoque(dto);
        return ResponseEntity.ok(estoque);
    }

    // Adicionar imagens a uma cor de um produto
    @PostMapping("/{id}/cores/{corId}/imagens")
    public ResponseEntity<List<ProdutoImagem>> adicionarImagens(
            @PathVariable Long id,
            @PathVariable Long corId,
            @RequestBody List<String> urls) {
        List<ProdutoImagem> imagens = produtoService.adicionarImagens(corId, urls);
        return ResponseEntity.ok(imagens);
    }

    @PostMapping("/completo")
    public ResponseEntity<ProdutoDTO> criarProdutoCompleto(@RequestBody ProdutoCompletoDTO dto) throws IOException {
        ProdutoDTO produto = produtoService.criarProdutoCompleto(dto);
        return ResponseEntity.ok(produto);
    }
}