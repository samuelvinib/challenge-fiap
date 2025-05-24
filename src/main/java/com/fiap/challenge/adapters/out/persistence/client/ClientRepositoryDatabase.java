package com.fiap.challenge.adapters.out.persistence.client;

import com.fiap.challenge.domain.entities.Cliente;
import com.fiap.challenge.domain.port.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryDatabase implements ClientRepository {

    private final ClientJpaRepository springDataClientJpaRepository;

    public ClientRepositoryDatabase(ClientJpaRepository springDataClientJpaRepository) {
        this.springDataClientJpaRepository = springDataClientJpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClientEntity entity = ClientEntity.fromDomain(cliente);
        ClientEntity saved = springDataClientJpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public List<Cliente> findAll() {
        return springDataClientJpaRepository.findAll()
                .stream()
                .map(ClientEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return springDataClientJpaRepository.findById(id).map(ClientEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataClientJpaRepository.deleteById(id);
    }
}
