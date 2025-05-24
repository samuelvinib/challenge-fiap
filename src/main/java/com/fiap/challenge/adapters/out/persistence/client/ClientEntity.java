package com.fiap.challenge.adapters.out.persistence.client;

import com.fiap.challenge.domain.entities.Cliente;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "clientes")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    public ClientEntity() {}

    public ClientEntity(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public Cliente toDomain() {
        return new Cliente(this.cpf, this.nome, this.email); // cpf aqui será encapsulado no VO Cpf
    }

    public static ClientEntity fromDomain(Cliente cliente) {
        return new ClientEntity(
                cliente.getCpf().getValue(), // pegar o valor do VO
                cliente.getNome(),
                cliente.getEmail()
        );
    }
}