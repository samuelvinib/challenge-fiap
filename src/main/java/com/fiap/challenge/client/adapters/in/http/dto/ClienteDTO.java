package com.fiap.challenge.client.adapters.in.http.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteDTO {
    private String cpf;
    private String nome;
    private String email;

    public ClienteDTO() {}

    public ClienteDTO(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

}
