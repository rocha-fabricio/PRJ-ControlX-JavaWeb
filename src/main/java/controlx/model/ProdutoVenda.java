package controlx.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Transactional
public class ProdutoVenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private BigDecimal preco;

	private BigDecimal qtd;

	private String tipoUn;

	@JsonIgnore
	private BigDecimal estoqueMin;

//	@JsonIgnore
//	@NotNull(message = "Escolha uma categoria")
//	@ManyToOne
//	@JoinColumn(name = "categoria_id")
//	private Categoria categoria = new Categoria();

//	@JsonIgnore
//	@NotNull(message = "Digite a url da imagem.")
//	@NotEmpty(message = "Digite a url da imagem.")
//	private String imagemUrl;

	@Value("0")
	private String codigoBarras;
	
//	@JsonIgnore
//	@NotNull(message = "Escolha um fornecedor")
//	@ManyToOne
//	@JoinColumn(name = "fornecedor_id")
//	private Fornecedor fornecedor = new Fornecedor();

//	@JsonIgnore
//	@Value("false")
//	private boolean deleted;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="venda_id")
	private Venda venda = new Venda();

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

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		venda.getProdutos().clear();
		this.venda = venda;
	}
	
	public void setVendaId(Long id) {
		this.venda.setId(id);
	}
	
}
