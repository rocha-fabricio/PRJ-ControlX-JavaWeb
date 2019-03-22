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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controlx.model.Categoria;
import controlx.repository.CategoriaRepository;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/cadastrarCategoria")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("formCategoria");
		modelAndView.addObject("categoriaObj", new Categoria());
		return modelAndView;
	}

	@GetMapping("/editarCategoria/{idcategoria}")
	public ModelAndView editar(@PathVariable("idcategoria") Long idcategoria) {
		Optional<Categoria> categoria = categoriaRepository.findById(idcategoria);
		ModelAndView modelAndView = new ModelAndView("/formCategoria");
		modelAndView.addObject("categoriaObj", categoria.get());
		modelAndView.addObject("edit", "");
		modelAndView.addObject("id", idcategoria);
		return modelAndView;
	}

	@GetMapping("/removerCategoria/{idcategoria}")
	public ModelAndView excluir(@PathVariable("idcategoria") Long idcategoria) {
		categoriaRepository.deleteById(idcategoria);
		return listarTodos();
	}

	@PostMapping("**/salvarCategoria")
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
		return listarTodos();
	}

	@GetMapping("**/categorias")
	public ModelAndView listarTodos() {
		ModelAndView andView = new ModelAndView("categorias");
		Iterable<Categoria> categoriasIt = categoriaRepository.findAll();
		andView.addObject("categorias", categoriasIt);
		return andView;
	}

	@PostMapping("**/pesquisarCategoria")
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
