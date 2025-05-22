package com.fiap.challenge.domain.client.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Client ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Client name", example = "John Doe")
    @Column(length = 100, nullable = false)
    private String name;

    @Schema(description = "Client email", example = "john@example.com")
    @Column(length = 100, nullable = false)
    private String email;

    @Schema(description = "Client CPF", example = "12345678901")
    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    public Client(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.cpf = phone;
    }

    public Client() {

    }
}
