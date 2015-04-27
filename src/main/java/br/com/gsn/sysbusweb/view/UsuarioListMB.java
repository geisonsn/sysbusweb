package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.PerfilUsuarioBC;
import br.com.gsn.sysbusweb.business.UsuarioBC;
import br.com.gsn.sysbusweb.domain.PerfilUsuario;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Parameter;

@ViewController
@NextView("./usuario_edit.jsf")
@PreviousView("./usuario_list.jsf")
public class UsuarioListMB extends AbstractListPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject 
	private PerfilUsuarioBC perfilUsuarioBC;

	private List<Usuario> listUsuario;

	private Usuario objetoExcluido;

	private String nome;
	
	private PerfilEnum[] pefisSelecionados;
	
	@Inject
	private Parameter<String> id;
	
	@PostConstruct
	public void init() {
		if (this.listUsuario == null) {
			this.listUsuario = handleResultList();
		}
	}

	@Override
	protected List<Usuario> handleResultList() {
		return this.usuarioBC.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				usuarioBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

	@Transactional
	public void deletar() {
		this.usuarioBC.delete(objetoExcluido.getId());
		this.listUsuario = handleResultList();
	}
	
	public void pesquisar() {
		this.listUsuario = usuarioBC.findByNome(this.getNome());
	}

	public List<Usuario> getListUsuario() {
		return this.listUsuario;
	}

	public Usuario getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(Usuario objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	private PerfilEnum perfil;
	
	public PerfilEnum getPerfil() {
		return perfil;
	}
	
	

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public PerfilEnum[] getListPerfil() {
		return PerfilEnum.values();
	}
	
	public List<PerfilUsuario> listPerfilUsuario() {
		Parameter<String> id2 = this.id;
		return perfilUsuarioBC.findByIdUsuario(1l);
	}
	
	public String perfilUsuario() {
		return "./perfil_usuario.jsf";
	}
	
	public String getUsuario() {
		return usuarioBC.load(Long.valueOf(id.getValue())).getNome();
	}

	public PerfilEnum[] getPefisSelecionados() {
		return pefisSelecionados;
	}

	public void setPefisSelecionados(PerfilEnum[] pefisSelecionados) {
		this.pefisSelecionados = pefisSelecionados;
	}
	
	public void salvarPerfil() {
		System.out.println(this.pefisSelecionados);
	}
}