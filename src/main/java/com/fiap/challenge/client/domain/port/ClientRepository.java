package com.fiap.challenge.client.domain.port;

import com.fiap.challenge.client.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    void deleteById(Long id);
}