package controlx.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import controlx.model.ProdutoVenda;
import controlx.model.Venda;

@Repository
@Transactional
public interface VendaRepository extends CrudRepository<Venda, Long>{
	
	@Query("SELECT v FROM Venda v WHERE v.id = ?1")
	List <Venda> findVendaById(Long id);
	
	@Query("SELECT v FROM Venda v WHERE v.data between :start and :end")
	List <Venda> findVendaByData(LocalDate start, LocalDate end);
	
	@Query("SELECT p FROM ProdutoVenda p WHERE p.venda.id = ?1")
	List <ProdutoVenda> findProdutosByIdVenda(Long id);
	
}