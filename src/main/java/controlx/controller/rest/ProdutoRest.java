package controlx.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controlx.model.Produto;
import controlx.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/produtosRest")
public class ProdutoRest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping(value = "/listarTodos", produces = "application/json")
	public ResponseEntity<List<Produto>> listarTodosRest() {
		List<Produto> produtos = (List<Produto>) produtoRepository.findAll();
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
}
