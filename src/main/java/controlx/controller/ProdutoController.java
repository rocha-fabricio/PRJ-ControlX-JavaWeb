package controlx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import controlx.model.Produto;
import controlx.repository.ProdutoRepository;

@Controller
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/cadastrarProduto")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("/formProduto");
		modelAndView.addObject("produtoObj", new Produto());
		return modelAndView;
	}

	@GetMapping("/editarProduto/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Long idproduto) {
		Optional<Produto> produto = produtoRepository.findById(idproduto);
		ModelAndView modelAndView = new ModelAndView("/formProduto");
		modelAndView.addObject("produtoObj", produto.get());
		modelAndView.addObject("edit", "");
		modelAndView.addObject("id", idproduto);
		return modelAndView;
	}

	@GetMapping("/removerProduto/{idproduto}")
	public ModelAndView excluir(@PathVariable("idproduto") Long idproduto) {
		produtoRepository.deleteById(idproduto);
		return listarTodos();
	}

	@PostMapping("**/salvarProduto")
	public ModelAndView salvar(@Valid Produto produto, BindingResult bindingResult) {
		//Verifica erros no formulário
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("/formProduto");
			modelAndView.addObject("produtoObj", produto);
			
			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
		}
		
		//Se não tiver erros...
		produtoRepository.save(produto);
		return listarTodos();
	}

	@GetMapping("**/estoque")
	public ModelAndView listarTodos() {
		ModelAndView andView = new ModelAndView("estoque");
		Iterable<Produto> produtosIt = produtoRepository.findAll();
		andView.addObject("produtos", produtosIt);
		return andView;
	}

	@PostMapping("**/pesquisarProduto")
	public ModelAndView listarByName(@RequestParam("pesquisa") String pesquisa,
			@RequestParam("tipoPesquisa") String tipoPesquisa) {
		ModelAndView modelAndView = new ModelAndView("/estoque");
		if (tipoPesquisa.equals("nome")) {
			modelAndView.addObject("produtos", produtoRepository.findProdutoByName(pesquisa));
			return modelAndView;
		} else if (tipoPesquisa.equals("id")) {
			modelAndView.addObject("produtos", produtoRepository.findProdutoById(Long.parseLong(pesquisa)));
			return modelAndView;
		}

		return modelAndView;
	}
}
