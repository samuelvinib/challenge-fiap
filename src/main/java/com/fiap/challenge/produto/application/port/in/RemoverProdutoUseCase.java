package com.fiap.challenge.produto.application.port.in;

public interface RemoverProdutoUseCase {
    boolean executar(Long id); // Retorna true se removido com sucesso, false caso contrário (ex: não encontrado)
}