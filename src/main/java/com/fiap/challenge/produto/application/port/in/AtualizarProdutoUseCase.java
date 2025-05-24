package com.fiap.challenge.produto.application.port.in;

import com.fiap.challenge.produto.domain.entities.Produto;
import com.fiap.challenge.produto.adapters.in.http.dto.ProdutoDTO; // Assumindo que o DTO existente será usado
import java.util.Optional;

public interface AtualizarProdutoUseCase {
    Optional<Produto> executar(Long id, ProdutoDTO produtoDTO);
}