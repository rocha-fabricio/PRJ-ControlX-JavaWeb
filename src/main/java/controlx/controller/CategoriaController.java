package controlx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controlx.model.Categoria;
import controlx.model.Produto;
import controlx.repository.CategoriaRepository;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("formCategoria");
		modelAndView.addObject("categoriaObj", new Categoria());
		return modelAndView;
	}

	@GetMapping("/editar/{idcategoria}")
	public ModelAndView editar(@PathVariable("idcategoria") Long idcategoria) {
		Optional<Categoria> categoria = categoriaRepository.findById(idcategoria);
		ModelAndView modelAndView = new ModelAndView("/formCategoria");
		modelAndView.addObject("categoriaObj", categoria.get());
		modelAndView.addObject("edit", "");
		modelAndView.addObject("id", idcategoria);
		return modelAndView;
	}

	@GetMapping("/remover/{idcategoria}")
	public ModelAndView excluir(@PathVariable("idcategoria") Long idcategoria) {
		Optional<Categoria> c = categoriaRepository.findById(idcategoria);
		Categoria categoria = c.get();
		categoria.setDeleted(true);
		categoriaRepository.save(categoria);
		ModelAndView modelAndView = new ModelAndView("redirect:/categorias");
		return modelAndView;
	}

	@PostMapping("**/salvar")
	public ModelAndView salvar(@Valid Categoria categoria, BindingResult bindingResult) {
		//Verifica erros no formulário
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("/formCategoria");
			modelAndView.addObject("categoriaObj", categoria);
			
			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
		}
		
		//Se não tiver erros...
		categoriaRepository.save(categoria);
		ModelAndView modelAndView = new ModelAndView("redirect:/categorias");
		return modelAndView;
	}

	@GetMapping("**/")
	public ModelAndView listarTodos() {
		ModelAndView andView = new ModelAndView("categorias");
		Iterable<Categoria> categoriasIt = categoriaRepository.findAll();
		andView.addObject("categorias", categoriasIt);
		return andView;
	}

	@PostMapping("**/pesquisar")
	public ModelAndView listarByName(@RequestParam("pesquisa") String pesquisa,
			@RequestParam("tipoPesquisa") String tipoPesquisa) {
		ModelAndView modelAndView = new ModelAndView("/categorias");
		if (tipoPesquisa.equals("nome")) {
			modelAndView.addObject("categorias", categoriaRepository.findCategoriaByName(pesquisa));
			return modelAndView;
		} else if (tipoPesquisa.equals("id")) {
			modelAndView.addObject("categorias", categoriaRepository.findCategoriaById(Long.parseLong(pesquisa)));
			return modelAndView;
		}

		return modelAndView;
	}
}
