package controlx.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

@Entity
public class Fornecedor {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "Digite um nome.")
	@NotEmpty(message = "Digite um nome.")
    private String nome;
	
	@NotNull(message = "Digite um CNPJ.")
	@NotEmpty(message = "Digite um CNPJ.")
    private String cnpj;
	
	@NotNull(message = "Digite um telefone.")
	@NotEmpty(message = "Digite um telefone.")
    private String telefone;
	
	@NotNull(message = "Digite um CEP.")
	@NotEmpty(message = "Digite um CEP.")
    private String cep;
	
	@NotNull(message = "Digite um número.")
	@NotEmpty(message = "Digite um número.")
    private Long num;
	
	@NotNull(message = "Digite uma rua.")
	@NotEmpty(message = "Digite uma rua.")
    private String rua;
	
	@NotNull(message = "Digite um complemento.")
	@NotEmpty(message = "Digite um complemento.")
    private String comp;
	
	@NotNull(message = "Digite um bairro.")
	@NotEmpty(message = "Digite um bairro.")
    private String bairro;
	
	@NotNull(message = "Digite uma cidade.")
	@NotEmpty(message = "Digite uma cidade.")
    private String cidade;
	
	@NotNull(message = "Digite um estado.")
	@NotEmpty(message = "Digite um estado.")
    private String estado;
	
	@OneToMany(mappedBy = "fornecedor", targetEntity = Produto.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Produto> produtos;
	
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
