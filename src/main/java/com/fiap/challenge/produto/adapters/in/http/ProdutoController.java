package com.fiap.challenge.produto.adapters.in.http;

import com.fiap.challenge.produto.adapters.in.http.dto.ProdutoDTO;
import com.fiap.challenge.produto.application.port.in.*; // Importa todas as interfaces de Use Case
import com.fiap.challenge.produto.domain.entities.Categoria;
import com.fiap.challenge.produto.domain.entities.Produto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produto Controller", description = "Operações CRUD para gerenciamento de produtos")
public class ProdutoController {

    // Injeção das interfaces de Use Case
    private final CriarProdutoUseCase criarProdutoUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;
    private final RemoverProdutoUseCase removerProdutoUseCase;
    private final BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase;
    private final BuscarProdutoPorCategoriaUseCase buscarProdutoPorCategoriaUseCase;
    private final ListarTodosProdutosUseCase listarTodosProdutosUseCase;

    public ProdutoController(CriarProdutoUseCase criarProdutoUseCase,
                             AtualizarProdutoUseCase atualizarProdutoUseCase,
                             RemoverProdutoUseCase removerProdutoUseCase,
                             BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase,
                             BuscarProdutoPorCategoriaUseCase buscarProdutoPorCategoriaUseCase,
                             ListarTodosProdutosUseCase listarTodosProdutosUseCase) {
        this.criarProdutoUseCase = criarProdutoUseCase;
        this.atualizarProdutoUseCase = atualizarProdutoUseCase;
        this.removerProdutoUseCase = removerProdutoUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.buscarProdutoPorCategoriaUseCase = buscarProdutoPorCategoriaUseCase;
        this.listarTodosProdutosUseCase = listarTodosProdutosUseCase;
    }

    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o produto")
    })
    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO dto) {
        try {
            Produto novoProduto = criarProdutoUseCase.executar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoDTO.fromDomain(novoProduto));
        } catch (IllegalArgumentException e) { // Captura exceções de validação do domínio ou DTO
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) { // Outras exceções inesperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar produto: " + e.getMessage());
        }
    }

    @Operation(summary = "Editar produto existente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização ou categoria inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> editarProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        try {
            Optional<Produto> produtoAtualizadoOptional = atualizarProdutoUseCase.executar(id, dto);
            return produtoAtualizadoOptional
                    .map(produto -> ResponseEntity.ok(ProdutoDTO.fromDomain(produto)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) { // Validação do DTO ou da entidade Produto
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) { // Ex.: ApplicationServiceException para categoria inválida
            if (e.getMessage() != null && e.getMessage().contains("Categoria inválida")) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    @Operation(summary = "Remover produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        boolean removido = removerProdutoUseCase.executar(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "400", description = "Categoria inválida")
    })
    @GetMapping("/categoria/{categoriaNome}")
    public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoriaNome) {
        try {
            Categoria categoriaEnum = Categoria.fromString(categoriaNome.toUpperCase());
            List<Produto> produtos = buscarProdutoPorCategoriaUseCase.executar(categoriaEnum);
            List<ProdutoDTO> dtos = produtos.stream()
                    .map(ProdutoDTO::fromDomain)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) { // Se Categoria.fromString falhar
            return ResponseEntity.badRequest().body("Categoria inválida: " + categoriaNome);
        }
    }

    @Operation(summary = "Listar todos os produtos cadastrados")
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
        List<Produto> produtos = listarTodosProdutosUseCase.executar();
        List<ProdutoDTO> dtos = produtos.stream()
                .map(ProdutoDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        return buscarProdutoPorIdUseCase.executar(id)
                .map(produto -> ResponseEntity.ok(ProdutoDTO.fromDomain(produto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}