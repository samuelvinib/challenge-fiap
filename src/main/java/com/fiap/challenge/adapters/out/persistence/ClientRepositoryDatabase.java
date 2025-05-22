package com.fiap.challenge.adapters.out.persistence;

import com.fiap.challenge.domain.cliente.port.ClientRepository;
import com.fiap.challenge.domain.entities.cliente.Cliente;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryDatabase implements ClientRepository {

    private final SpringDataClientJpaRepository jpaRepository;

    public ClientRepositoryDatabase(SpringDataClientJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return jpaRepository.save(cliente); // pode envolver conversão de entidade
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        jpaRepository.delete(cliente);
    }
}