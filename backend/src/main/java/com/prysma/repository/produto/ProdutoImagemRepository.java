package com.prysma.repository.produto;

import com.prysma.model.produto.ProdutoImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Long> {
}
