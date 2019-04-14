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
import controlx.model.Fornecedor;
import controlx.repository.FornecedorRepository;

@RestController
@RequestMapping(value = "/rest/fornecedores")
public class FornecedorRest {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@GetMapping(value = "/listarTodos", produces = "application/json")
	public ResponseEntity<List<Fornecedor>> listar() {
		List<Fornecedor> fornecedores = (List<Fornecedor>) fornecedorRepository.findAll();
		return new ResponseEntity<List<Fornecedor>>(fornecedores, HttpStatus.OK);
	}

	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Fornecedor> cadastrar(@RequestBody Fornecedor fornecedor) {
	
		Fornecedor f = fornecedorRepository.save(fornecedor);
		return new ResponseEntity<Fornecedor>(f, HttpStatus.OK);
	}

	@PostMapping(value = "/editar", produces = "application/json")
	public ResponseEntity<Fornecedor> editar(@RequestBody Fornecedor fornecedor) {
		Fornecedor f = fornecedorRepository.save(fornecedor);

		return new ResponseEntity<Fornecedor>(f, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletar/{id}", produces = "application/json")
	public HttpStatus delete(@PathVariable("id") Long id) {
		Optional<Fornecedor> f = fornecedorRepository.findById(id);
		f.get().setDeleted(true);
		fornecedorRepository.save(f.get());
		
		return HttpStatus.OK;
	}
}
