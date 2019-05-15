package controlx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import controlx.model.Produto;
import controlx.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.nome LIKE %?1% AND deleted = 0")
	List <Usuario> findUsuarioByName(String nome);
	
	@Query("SELECT u FROM Usuario u WHERE u.id = ?1 AND deleted = 0")
	List <Usuario> findUsuarioById(Long id);
	
	@Override
	@Query("UPDATE Usuario SET deleted = 1 WHERE id = ?1")
	void deleteById(Long id);
	
	@Override
	@Query("SELECT u FROM Usuario u WHERE deleted = 0")
	Iterable<Usuario> findAll();
	
	@Query("SELECT u FROM Usuario u WHERE login = ?1 AND deleted = 0")
	Usuario findUserByLogin(String login);
	
}
