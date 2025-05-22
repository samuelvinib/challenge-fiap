package com.fiap.challenge.domain.entities.cliente;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ClienteTest {

    @Test
    public void deveLancarExcecaoQuandoCpfInvalido() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Cliente("12345678901", "Nome Teste", "teste@email.com"));
    }

    @Test
    public void deveCriarClienteComCpfValido() {
        Cliente cliente = new Cliente("52998224725", "João", "joao@email.com");
        Assertions.assertEquals("João", cliente.getNome());
        Assertions.assertEquals("joao@email.com", cliente.getEmail());
        Assertions.assertEquals("52998224725", cliente.getCpf().getValue());
    }
}
