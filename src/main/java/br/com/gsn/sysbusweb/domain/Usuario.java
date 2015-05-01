package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;

@Entity
@Table(name = "usuario")
@NamedQueries({
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
	@NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u where upper(u.nome) like :nome"),
	@NamedQuery(name = "Usuario.getByUsernameEPassword", query = "SELECT u FROM Usuario u where u.username = :username and u.password = :password"),
	@NamedQuery(name = "Usuario.getByUsernameEEmail", query = "SELECT u FROM Usuario u where lower(u.username) = :username or u.email = :email")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nome;

	private String sobrenome;

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty
	private String email;

	@OneToMany(mappedBy = "usuario", orphanRemoval = true)
	private List<PerfilUsuario> listPerfilUsuario;

	public Usuario() {
	}

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PerfilUsuario> getListPerfilUsuario() {
		return listPerfilUsuario;
	}

	public void setListPerfilUsuario(List<PerfilUsuario> listPerfilUsuario) {
		this.listPerfilUsuario = listPerfilUsuario;
	}
	
	public boolean isAdministrador() {
		for (PerfilUsuario perfilUsuario : listPerfilUsuario) {
			return perfilUsuario.getPerfil() == PerfilEnum.ADMINISTRADOR;
		}
		return false;
	}
	
	public boolean isMantenedor() {
		for (PerfilUsuario perfilUsuario : listPerfilUsuario) {
			return perfilUsuario.getPerfil() == PerfilEnum.MANTENEDOR;
		}
		return false;
	}
	
	public boolean isGestor() {
		for (PerfilUsuario perfilUsuario : listPerfilUsuario) {
			return perfilUsuario.getPerfil() == PerfilEnum.GESTOR;
		}
		return false;
	}
	
	public boolean isCliente() {
		for (PerfilUsuario perfilUsuario : listPerfilUsuario) {
			return perfilUsuario.getPerfil() == PerfilEnum.CLIENTE;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((sobrenome == null) ? 0 : sobrenome.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sobrenome == null) {
			if (other.sobrenome != null)
				return false;
		} else if (!sobrenome.equals(other.sobrenome))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}