package controlx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import controlx.model.Fornecedor;
import controlx.model.Produto;

@Repository
@Transactional
public interface FornecedorRepository extends CrudRepository<Fornecedor, Long>{
	
	@Query("SELECT f FROM Fornecedor f WHERE f.nome LIKE %?1%")
	List <Fornecedor> findFornecedorByName(String nome);
	
	@Query("SELECT f FROM Fornecedor f WHERE f.id = ?1")
	List <Fornecedor> findFornecedorById(Long id);
	
	@Override
	@Query("UPDATE Fornecedor SET deleted = 1 WHERE id = ?1")
	void deleteById(Long id);
	
	@Override
	@Query("SELECT f FROM Fornecedor f WHERE deleted = 0")
	List <Fornecedor> findAll();
}
