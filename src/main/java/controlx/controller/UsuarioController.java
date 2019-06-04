package controlx.controller;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
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
import controlx.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView modelAndView = new ModelAndView("/formUsuario");
		modelAndView.addObject("usuarioObj", new Usuario());
		return modelAndView;
	}

	@GetMapping("/editar/{idusuario}")
	public ModelAndView editar(@PathVariable("idusuario") Long idusuario) {
		Optional<Usuario> usuario = usuarioRepository.findById(idusuario);
		ModelAndView modelAndView = new ModelAndView("/formUsuario");
		modelAndView.addObject("usuarioObj", usuario.get());
		modelAndView.addObject("edit", "");
		modelAndView.addObject("id", idusuario);
		return modelAndView;
	}

	@GetMapping("/remover/{idusuario}")
	public ModelAndView excluir(@PathVariable("idusuario") Long idusuario) {
		Optional<Usuario> u = usuarioRepository.findById(idusuario);
		u.get().setDeleted(true);
		usuarioRepository.save(u.get());
		ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
		return modelAndView;
	}

	@PostMapping("**/salvar")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult bindingResult) {
		//Verifica erros no formulário
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("/formUsuario");
			modelAndView.addObject("usuarioObj", usuario);
			
			List<String> msg = new ArrayList<>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		//Se não tiver erros...
		usuarioRepository.save(usuario);
		ModelAndView modelAndView = new ModelAndView("redirect:/usuarios");
		return modelAndView;
	}

	@GetMapping("**/")
	public ModelAndView listarTodos() {
		ModelAndView andView = new ModelAndView("usuarios");
		Iterable<Usuario> usuariosIt = usuarioRepository.findAll();
		andView.addObject("usuarios", usuariosIt);
		return andView;
	}

	@PostMapping("**/pesquisar")
	public ModelAndView listarByName(@RequestParam("pesquisa") String pesquisa,
			@RequestParam("tipoPesquisa") String tipoPesquisa) {
		ModelAndView modelAndView = new ModelAndView("/usuarios");
		if (tipoPesquisa.equals("nome")) {
			modelAndView.addObject("usuarios", usuarioRepository.findUsuarioByName(pesquisa));
			return modelAndView;
		} else if (tipoPesquisa.equals("id")) {
			modelAndView.addObject("usuarios", usuarioRepository.findUsuarioById(Long.parseLong(pesquisa)));
			return modelAndView;
		}

		return modelAndView;
	}
}
