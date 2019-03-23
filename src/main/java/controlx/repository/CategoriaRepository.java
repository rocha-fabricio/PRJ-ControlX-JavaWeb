package controlx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import controlx.model.Categoria;
import controlx.model.Produto;

@Repository
@Transactional
public interface CategoriaRepository extends CrudRepository<Categoria, Long>{
	
	@Query("SELECT c FROM Categoria c WHERE c.nome LIKE %?1%")
	List <Categoria> findCategoriaByName(String nome);
	
	@Query("SELECT c FROM Categoria c WHERE c.id = ?1")
	List <Categoria> findCategoriaById(Long id);
	
	@Override
	@Query("UPDATE Categoria SET deleted = 1 WHERE id = ?1")
	void deleteById(Long id);
	
	@Override
	@Query("SELECT c FROM Categoria c WHERE deleted = 0")
	List <Categoria> findAll();
}
