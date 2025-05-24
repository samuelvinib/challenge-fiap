package com.fiap.challenge.adapters.in.http.client.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientDTO {
    private String cpf;
    private String nome;
    private String email;

    public ClientDTO() {}

    public ClientDTO(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

}
