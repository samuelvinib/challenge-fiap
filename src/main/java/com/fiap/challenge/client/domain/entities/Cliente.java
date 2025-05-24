package com.fiap.challenge.client.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cliente {
    private Cpf cpf;
    private String nome;
    private String email;

    public Cliente(String cpf, String nome, String email) {
        this.cpf = new Cpf(cpf);
        this.nome = nome;
        this.email = email;
    }

}