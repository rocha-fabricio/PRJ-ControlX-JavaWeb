package controlx.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import controlx.model.Produto;
import controlx.model.Usuario;
import controlx.repository.CategoriaRepository;
import controlx.repository.FornecedorRepository;
import controlx.repository.ProdutoRepository;

@Controller
@RequestMapping("/estoque")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("/formProduto");
		modelAndView.addObject("produtoObj", new Produto());
		modelAndView.addObject("categorias", categoriaRepository.findAll());
		modelAndView.addObject("fornecedores", fornecedorRepository.findAll());
		return modelAndView;
	}

	@GetMapping("/editar/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Long idproduto) {
		Optional<Produto> produto = produtoRepository.findById(idproduto);
		ModelAndView modelAndView = new ModelAndView("/formProduto");
		modelAndView.addObject("produtoObj", produto.get());
		modelAndView.addObject("categorias", categoriaRepository.findAll());
		modelAndView.addObject("fornecedores", fornecedorRepository.findAll());
		modelAndView.addObject("edit", "");
		modelAndView.addObject("id", idproduto);
		return modelAndView;
	}

	@GetMapping("/remover/{idproduto}")
	public ModelAndView excluir(@PathVariable("idproduto") Long idproduto) {
		Optional<Produto> p = produtoRepository.findById(idproduto);
		p.get().setDeleted(true);
		produtoRepository.save(p.get());
		ModelAndView modelAndView = new ModelAndView("redirect:/estoque");
		return modelAndView;
	}

	@PostMapping("**/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult bindingResult) {
		// Verifica erros no formulário
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("/formProduto");
			modelAndView.addObject("produtoObj", produto);
			modelAndView.addObject("categorias", categoriaRepository.findAll());
			modelAndView.addObject("fornecedores", fornecedorRepository.findAll());

			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}

			modelAndView.addObject("msg", msg);
			return modelAndView;
		}

		// Se não tiver erros...
		produtoRepository.save(produto);
		ModelAndView modelAndView = new ModelAndView("redirect:/estoque");
		return modelAndView;
	}

	@GetMapping("**/")
	public ModelAndView listarTodos() {
		ModelAndView andView = new ModelAndView("estoque");
		Iterable<Produto> produtosIt = produtoRepository.findAll();
		andView.addObject("produtos", produtosIt);
		return andView;
	}

	@PostMapping("**/pesquisar")
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
