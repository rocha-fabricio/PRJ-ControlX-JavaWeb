package controlx.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Digite o nome.")
	@NotEmpty(message = "Digite o nome.")
	private String nome;
	
	@NotNull(message = "Digite o CPF")
	@NotEmpty(message = "Digite o CPF.")
	private String cpf;
	
	@NotNull(message = "Informe o sexo.")
	private Boolean sexo;
	
	private Date dataNasc;
	
	@NotNull(message = "Digite um telefone.")
	@NotEmpty(message = "Digite um telefone.")
	private String telefone;

	@NotNull(message = "Digite o CEP.")
	@NotEmpty(message = "Digite o CEP.")
	private String cep;
	
	@NotNull(message = "Digite o número.")
	@NotEmpty(message = "Digite o número.")
	private String num;
	
	@NotNull(message = "Digite a rua.")
	@NotEmpty(message = "Digite a rua.")
	private String rua;
	
	@NotNull(message = "Digite o complemento.")
	@NotEmpty(message = "Digite o complemento.")
	private String comp;
	
	@NotNull(message = "Digite o bairro.")
	@NotEmpty(message = "Digite o bairro.")
	private String bairro;
	
	@NotNull(message = "Digite a cidade.")
	@NotEmpty(message = "Digite a cidade.")
	private String cidade;
	
	@NotNull(message = "Selecione o estado.")
	@NotEmpty(message = "Selecione o estado.")
	private String estado;
	
	@NotNull(message = "Digite o login.")
	@NotEmpty(message = "Digite o login.")
	private String login;
	
	@NotNull(message = "Digite a senha.")
	@NotEmpty(message = "Digite a senha.")
	private String senha;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role = new Role();

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
}
