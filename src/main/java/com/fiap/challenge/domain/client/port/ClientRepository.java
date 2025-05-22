package com.fiap.challenge.domain.client.port;

import com.fiap.challenge.domain.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{
    // Custom query methods can be defined here if needed
}
