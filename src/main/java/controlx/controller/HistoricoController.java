package controlx.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controlx.model.ProdutoVenda;
import controlx.model.Venda;
import controlx.repository.VendaRepository;

@Controller
@RequestMapping("/historico")
public class HistoricoController {

	@Autowired
	private VendaRepository vendaRepository;

	@GetMapping("/visualizar/{idvenda}")
	public ModelAndView editar(@PathVariable("idvenda") Long idvenda) {
		Optional<Venda> venda = vendaRepository.findById(idvenda);
		List<ProdutoVenda> produtosVenda = vendaRepository.findProdutosByIdVenda(idvenda);
		ModelAndView modelAndView = new ModelAndView("formVenda");
		modelAndView.addObject("vendaObj", venda.get());
		modelAndView.addObject("produtos", produtosVenda);
		return modelAndView;
	}

	@PostMapping("**/buscar")
	public ModelAndView listarPorDate(@RequestParam("start") String start,
			@RequestParam("end") String end) {
		ModelAndView andView = new ModelAndView("historicoVendas");
		
		String[] data = start.split("-");
		LocalDate inicio = LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
		
		data = end.split("-");
		LocalDate fim = LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
		Iterable<Venda> vendasIt = vendaRepository.findVendaByData(inicio, fim);
		andView.addObject("vendas", vendasIt);
		return andView;
	}
	
	@GetMapping("**/")
	public ModelAndView dataHistorico() {
		ModelAndView andView = new ModelAndView("historico");
		return andView;
	}
}
