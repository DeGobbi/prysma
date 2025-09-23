package com.prysma.repository.produto;

import com.prysma.model.produto.ProdutoCor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCorRepository extends JpaRepository<ProdutoCor, Long> {
}
