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
import controlx.model.Usuario;
import controlx.repository.FornecedorRepository;
import controlx.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/rest/usuarios")
public class UsuarioRest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/listarTodos", produces = "application/json")
	public ResponseEntity<List<Usuario>> listar() {
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscar/{id}", produces = "application/json")
	public ResponseEntity<Optional<Usuario>> listarById(@PathVariable("id") Long id) {
		Optional<Usuario> u = usuarioRepository.findById(id);
		return new ResponseEntity<Optional<Usuario>>(u, HttpStatus.OK);
	}


	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
	
		Usuario u = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(u, HttpStatus.OK);
	}

	@PostMapping(value = "/editar", produces = "application/json")
	public ResponseEntity<Usuario> editar(@RequestBody Usuario usuario) {
		Usuario u = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(u, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deletar/{id}", produces = "application/json")
	public HttpStatus delete(@PathVariable("id") Long id) {
		Optional<Usuario> u = usuarioRepository.findById(id);
		u.get().setDeleted(true);
		usuarioRepository.save(u.get());
		
		return HttpStatus.OK;
	}
}
