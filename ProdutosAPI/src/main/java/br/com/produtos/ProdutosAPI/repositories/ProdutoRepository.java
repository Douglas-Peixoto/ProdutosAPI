package br.com.produtos.ProdutosAPI.repositories;

import br.com.produtos.ProdutosAPI.entities.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
}
