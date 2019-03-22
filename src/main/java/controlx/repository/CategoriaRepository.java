package controlx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import controlx.model.Categoria;

@Repository
@Transactional
public interface CategoriaRepository extends CrudRepository<Categoria, Long>{
	
	@Query("SELECT c FROM Categoria c WHERE c.nome LIKE %?1%")
	List <Categoria> findCategoriaByName(String nome);
	
	@Query("SELECT c FROM Categoria c WHERE c.id = ?1")
	List <Categoria> findCategoriaById(Long id);
}
