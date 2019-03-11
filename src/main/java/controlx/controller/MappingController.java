package controlx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MappingController {
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}
	
	@GetMapping("/formProduto")
	public String formProduto() {
		return "formproduto";
	}
	
}
