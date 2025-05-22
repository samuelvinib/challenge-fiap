package com.fiap.challenge.adapters.in.http;

import com.fiap.challenge.adapters.out.ClientRepositoryDatabase;
import com.fiap.challenge.domain.client.entity.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client Controller", description = "Operations related to clients")
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientRepositoryDatabase repository;

    public ClientController(ClientRepositoryDatabase repository) {
        this.repository = repository;
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    @GetMapping
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Operation(summary = "Save a client")
    @PostMapping
    public ResponseEntity<Client> save(@RequestBody Client client) {
        Client savedClient = repository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
}