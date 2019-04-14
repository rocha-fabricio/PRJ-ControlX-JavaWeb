package controlx.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import controlx.model.Categoria;
import controlx.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "/rest/categorias")
public class CategoriaRest {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping(value = "/listarTodas", produces = "application/json")
	public ResponseEntity<List<Categoria>> listar() {
		List<Categoria> categorias = (List<Categoria>) categoriaRepository.findAll();
		return new ResponseEntity<List<Categoria>>(categorias, HttpStatus.OK);
	}

	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) {
	
		Categoria c = categoriaRepository.save(categoria);
		return new ResponseEntity<Categoria>(c, HttpStatus.OK);
	}

	@PostMapping(value = "/editar", produces = "application/json")
	public ResponseEntity<Categoria> editar(@RequestBody Categoria categoria) {
		Categoria c = categoriaRepository.save(categoria);

		return new ResponseEntity<Categoria>(c, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletar/{id}", produces = "application/json")
	public HttpStatus delete(@PathVariable("id") Long id) {
		Optional<Categoria> c = categoriaRepository.findById(id);
		c.get().setDeleted(true);
		categoriaRepository.save(c.get());
		
		return HttpStatus.OK;
	}
}
