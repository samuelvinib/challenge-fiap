package com.fiap.challenge.adapters.out;

import com.fiap.challenge.domain.client.entity.Client;
import com.fiap.challenge.domain.client.port.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepositoryDatabase {
    private final ClientRepository clientRepository;

    public ClientRepositoryDatabase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}
