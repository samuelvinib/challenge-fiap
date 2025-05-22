package com.fiap.challenge.cliente.adapters.in.http;

import com.fiap.challenge.cliente.domain.entities.Cliente;
import com.fiap.challenge.cliente.domain.port.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cliente Controller", description = "Operations related to clients")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    @GetMapping
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Operation(summary = "Save a client")
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }
}
