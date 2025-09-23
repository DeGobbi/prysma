package com.prysma.repository.produto;

import com.prysma.model.produto.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque, Long> {
}
