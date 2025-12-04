package com.prysma.repository.produto;

import com.prysma.model.produto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByReferencia(String referencia);
}

