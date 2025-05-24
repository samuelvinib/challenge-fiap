package com.fiap.challenge.produto.adapters.out.persistence;

import com.fiap.challenge.produto.domain.entities.Produto;
import com.fiap.challenge.produto.domain.entities.Categoria;
import com.fiap.challenge.produto.domain.port.ProdutoRepository;
// Remova o @Qualifier se não houver outra implementação de ProdutoRepository
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository // Se esta for a única implementação, @Qualifier não é estritamente necessário no construtor que a injeta.
// Mas pode ser útil se você planeja ter múltiplas implementações (ex: in-memory para testes).
public class ProdutoRepositoryDatabase implements ProdutoRepository {

    private final ProdutoJpaRepository jpaRepository;

    public ProdutoRepositoryDatabase(ProdutoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Produto save(Produto produto) {
        ProdutoEntity entity = ProdutoEntity.fromDomain(produto);
        ProdutoEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Produto> findById(Long id) {
        return jpaRepository.findById(id).map(ProdutoEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produto> findAll() {
        return jpaRepository.findAll().stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // A lógica de verificar se existe antes de deletar foi movida para o UseCase (RemoverProdutoUseCase)
        // para que o UseCase possa retornar um booleano indicando sucesso/falha.
        // O JpaRepository.deleteById não lança exceção se o ID não for encontrado por padrão.
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produto> findByCategoria(Categoria categoria) {
        return jpaRepository.findByCategoria(categoria).stream()
                .map(ProdutoEntity::toDomain)
                .collect(Collectors.toList());
    }
}