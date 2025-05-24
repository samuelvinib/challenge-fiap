package com.fiap.challenge.client.adapters.in.http;

import com.fiap.challenge.client.adapters.in.http.dto.ClientDTO;
import com.fiap.challenge.client.domain.entities.Cliente;
import com.fiap.challenge.client.domain.port.ClientRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
@Tag(name = "Cliente Controller", description = "Operações relacionadas a clientes")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Operation(summary = "Cadastrar cliente")
    @PostMapping
    public ResponseEntity<ClientDTO> cadastrar(@RequestBody ClientDTO dto) {
        Cliente cliente = new Cliente(dto.getCpf(), dto.getNome(), dto.getEmail());
        Cliente salvo = clientRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(salvo));
    }

    @Operation(summary = "Listar clientes")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> listarTodos() {
        List<ClientDTO> clientes = clientRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    private ClientDTO toDTO(Cliente cliente) {
        return new ClientDTO(
                cliente.getCpf().getValue(),
                cliente.getNome(),
                cliente.getEmail()
        );
    }
}
