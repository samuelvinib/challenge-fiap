package com.fiap.challenge.adapters.in.http;

import com.fiap.challenge.domain.cliente.port.ClientRepository;
import com.fiap.challenge.domain.entities.cliente.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client Controller", description = "Operations related to clients")
@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    @GetMapping
    public List<Cliente> getAll() {
        return clientRepository.findAll();
    }

    @Operation(summary = "Save a client")
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        Cliente savedCliente = clientRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }
}
