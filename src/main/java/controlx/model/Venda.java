package controlx.model;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.NoRepositoryBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import controlx.repository.UsuarioRepository;

@Entity
@Transactional
public class Venda implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnoreProperties({"cpf","sexo","dataNasc","telefone","cep","num","rua","comp","bairro","cidade","estado","login","senha","imagemUrl"})
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private double valor = 0;
	
	private LocalDate data;
	
	private LocalTime hora;
	
	@OneToMany(targetEntity = ProdutoVenda.class, mappedBy = "venda", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ProdutoVenda> produtos = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
			this.hora = hora;
	}

	public void setData(LocalDate data) {
			this.data = data;
	}

	public List<ProdutoVenda> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoVenda> produtos) {		
		for (ProdutoVenda p : produtos) {
			p.setVenda(this);
		}
		
		this.produtos = produtos;
	}
		
}
