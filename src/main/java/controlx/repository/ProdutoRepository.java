package controlx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import controlx.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends CrudRepository<Produto, Long>{
	
	@Query("SELECT p FROM Produto p WHERE p.nome LIKE %?1%")
	List <Produto> findProdutoByName(String nome);
	
	@Query("SELECT p FROM Produto p WHERE p.id = ?1")
	List <Produto> findProdutoById(Long id);
}
