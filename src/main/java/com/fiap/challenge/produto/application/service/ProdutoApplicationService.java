package com.fiap.challenge.produto.application.service;

import com.fiap.challenge.produto.application.port.in.*;
import com.fiap.challenge.produto.domain.entities.Categoria;
import com.fiap.challenge.produto.domain.entities.Produto;
import com.fiap.challenge.produto.domain.port.ProdutoRepository;
import com.fiap.challenge.produto.adapters.in.http.dto.ProdutoDTO; // Usando o DTO da camada de adaptadores de entrada
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para a atomicidade das operações

import java.util.List;
import java.util.Optional;

@Service // Anotação do Spring para torná-lo um bean gerenciável e injetável
public class ProdutoApplicationService implements
        CriarProdutoUseCase,
        AtualizarProdutoUseCase,
        RemoverProdutoUseCase,
        BuscarProdutoPorIdUseCase,
        BuscarProdutoPorCategoriaUseCase,
        ListarTodosProdutosUseCase {

    private final ProdutoRepository produtoRepository;

    // Injeção de dependência do repositório
    public ProdutoApplicationService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional // Garante que a operação seja atômica
    @Override
    public Produto executar(ProdutoDTO produtoDTO) {
        Produto produto = produtoDTO.toDomain();
        // Aqui podem ser adicionadas lógicas de aplicação específicas antes de salvar,
        // como validações de regras de negócio que não estão na entidade,
        // ou coordenação com outros serviços/agregados.
        return produtoRepository.save(produto);
    }

    @Transactional
    @Override
    public Optional<Produto> executar(Long id, ProdutoDTO produtoDTO) {
        // Busca o produto existente
        Optional<Produto> produtoExistenteOptional = produtoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();
            // Atualiza os campos do produto existente com os dados do DTO
            produtoExistente.setNome(produtoDTO.getNome());
            try {
                // A conversão de String para Enum é feita aqui ou dentro do DTO/Entidade
                produtoExistente.setCategoria(Categoria.fromString(produtoDTO.getCategoria()));
            } catch (IllegalArgumentException e) {
                // Lançar uma exceção mais específica da camada de aplicação ou um erro de validação
                throw new ApplicationServiceException("Categoria inválida: " + produtoDTO.getCategoria(), e);
            }
            produtoExistente.setPreco(produtoDTO.getPreco());
            produtoExistente.setDescricao(produtoDTO.getDescricao());
            // Salva o produto atualizado
            return Optional.of(produtoRepository.save(produtoExistente));
        } else {
            return Optional.empty(); // Produto não encontrado para atualização
        }
    }

    @Transactional
    @Override
    public boolean executar(Long id) {
        if (produtoRepository.findById(id).isPresent()) {
            produtoRepository.deleteById(id);
            return true; // Remoção bem-sucedida
        }
        return false; // Produto não encontrado para remoção
    }

    @Transactional(readOnly = true) // Operação de leitura, não modifica dados
    @Override
    public Optional<Produto> executar(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Produto> executar(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Produto> executar() {
        return produtoRepository.findAll();
    }
}

// Exceção personalizada (opcional, mas uma boa prática para a camada de aplicação)
class ApplicationServiceException extends RuntimeException {
    public ApplicationServiceException(String message) {
        super(message);
    }

    public ApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}