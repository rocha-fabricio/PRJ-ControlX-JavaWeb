package controlx.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

@Entity
public class Produto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "Digite um nome.")
	@NotEmpty(message = "Digite um nome.")
    private String nome;
	
	@NotNull(message = "Digite um preço.")
    private BigDecimal preco;
	
	@NotNull(message = "Digite uma quantidade.")
    private BigDecimal qtd;
	
	@NotNull(message = "Escolha um tipo.")
	@NotEmpty(message = "Escolha um tipo.")
    private String tipoUn;
	
	@NotNull(message = "Digite um estoque mínimo.")
    private BigDecimal estoqueMin;
	
	@NotNull(message = "Escolha uma categoria")
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	@NotNull(message = "Escolha um fornecedor")
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	
	@Value("false")
	private boolean deleted;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public BigDecimal getQtd() {
		return qtd;
	}
	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}
	public String getTipoUn() {
		return tipoUn;
	}
	public void setTipoUn(String tipoUn) {
		this.tipoUn = tipoUn;
	}
	public BigDecimal getEstoqueMin() {
		return estoqueMin;
	}
	public void setEstoqueMin(BigDecimal estoqueMin) {
		this.estoqueMin = estoqueMin;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
}
