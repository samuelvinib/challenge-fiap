package com.fiap.challenge.domain.port;

import com.fiap.challenge.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    void deleteById(Long id);
}