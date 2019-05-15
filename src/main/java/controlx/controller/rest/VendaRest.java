package controlx.controller.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import controlx.model.Fornecedor;
import controlx.model.Produto;
import controlx.model.ProdutoVenda;
import controlx.model.Usuario;
import controlx.model.Venda;
import controlx.repository.FornecedorRepository;
import controlx.repository.UsuarioRepository;
import controlx.repository.VendaRepository;

@RestController
@RequestMapping(value = "/rest/venda")
public class VendaRest {

	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/listarTodas", produces = "application/json")
	public ResponseEntity<List<Venda>> listar() {
		List<Venda> vendas = (List<Venda>) vendaRepository.findAll();
		return new ResponseEntity<List<Venda>>(vendas, HttpStatus.OK);
	}

	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Venda> cadastrar(@RequestBody Venda venda) {
		String usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario user = usuarioRepository.findUserByLogin(usuarioLogado);
		venda.setUsuario(user);
		venda.setData(LocalDate.now());
		venda.setHora(LocalTime.now());
		Venda v = vendaRepository.save(venda);
		return new ResponseEntity<Venda>(v, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pesquisar/{id}", produces = "application/json")
	public ResponseEntity<Venda> pesquisarId(@PathVariable("id") Long id) {
		Optional<Venda> venda = vendaRepository.findById(id);
		return new ResponseEntity(venda, HttpStatus.OK);
	}
	
}
