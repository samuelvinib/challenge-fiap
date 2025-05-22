package com.fiap.challenge.adapters.out.persistence;

import com.fiap.challenge.domain.entities.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataClienteJpaRepository extends JpaRepository<Cliente, Long> {
}