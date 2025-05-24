package com.fiap.challenge.produto.adapters.in.http;

import com.fiap.challenge.produto.adapters.in.http.dto.ProdutoDTO;
import com.fiap.challenge.produto.domain.entities.Produto;
import com.fiap.challenge.produto.domain.entities.Categoria;
import com.fiap.challenge.produto.domain.port.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto Controller", description = "Operações CRUD para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    // Use @Qualifier se tiver múltiplos beans do mesmo tipo (ex: um em memória e outro real)
    public ProdutoController(@Qualifier("produtoRepositoryDatabase") ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o produto")
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO dto) {
        try {
            Produto produto = dto.toDomain();
            Produto novoProduto = produtoRepository.save(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoDTO.fromDomain(novoProduto));
        } catch (IllegalArgumentException e) {
            // Futuramente, pode-se retornar um DTO de erro com a mensagem e.getMessage()
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Editar produto existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> editarProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        try {
            Produto produtoParaAtualizar = dto.toDomain();
            // Garante que o ID do path seja usado, e não um ID possivelmente diferente no corpo do DTO
            produtoParaAtualizar.setId(id);
            return produtoRepository.update(id, produtoParaAtualizar)
                    .map(produtoAtualizado -> ResponseEntity.ok(ProdutoDTO.fromDomain(produtoAtualizado)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Remover produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        try {
            // Verifica se existe antes para dar um 404 adequado se não for o caso na deleteById
            if (produtoRepository.findById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) { // Caso o deleteById lance exceção por outro motivo
            return ResponseEntity.notFound().build(); // Ou badRequest dependendo da exceção
        }
    }

    @Operation(summary = "Buscar produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "400", description = "Categoria inválida")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoDTO>> buscarPorCategoria(@PathVariable String categoria) {
        try {
            // Padroniza a entrada da categoria para maiúsculas para corresponder ao Enum
            Categoria catEnum = Categoria.fromString(categoria.toUpperCase());
            List<ProdutoDTO> produtos = produtoRepository.findByCategoria(catEnum).stream()
                    .map(ProdutoDTO::fromDomain)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(produtos);
        } catch (IllegalArgumentException e) {
            // Categoria inválida resulta em bad request
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Listar todos os produtos cadastrados")
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
        List<ProdutoDTO> produtos = produtoRepository.findAll().stream()
                .map(ProdutoDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(ProdutoDTO.fromDomain(produto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}