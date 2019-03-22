package controlx.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "Digite um nome.")
	@NotEmpty(message = "Digite um nome.")
    private String nome;
	@OneToMany(mappedBy = "categoria", targetEntity = Produto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Produto> produtos;

	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
