
package br.com.gsn.sysbusweb.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.gsn.sysbusweb.domain.PerfilUsuario;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;
import br.com.gsn.sysbusweb.persistence.PerfilUsuarioDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class PerfilUsuarioBC extends DelegateCrud<PerfilUsuario, Long, PerfilUsuarioDAO> {
	private static final long serialVersionUID = 1L;
	
	public List<PerfilUsuario> findByIdUsuario(Long idUsuario) {
		return getDelegate().findByIdUsuario(idUsuario);
	}
	
	public PerfilEnum[] getPerfisSalvos(Long idUsuario) {
		
		List<PerfilUsuario> listPerfil = this.findByIdUsuario(idUsuario);
		
		if (listPerfil.isEmpty()) {
			return null;
		}
		
		List<PerfilEnum> perfis = new ArrayList<PerfilEnum>();
		for (PerfilUsuario perfilUsuario : listPerfil) {
			perfis.add(perfilUsuario.getPerfil());
		}
		
		return Arrays.copyOf(perfis.toArray(), perfis.size(), PerfilEnum[].class);
	}

	public void salvarPerfil(Long idUsuario, PerfilEnum[] perfisSelecionados) {
		List<PerfilUsuario> list = this.findByIdUsuario(idUsuario);
		if (list.isEmpty()) {
			for (PerfilEnum perfil : perfisSelecionados) {
				PerfilUsuario perfilUsuario = new PerfilUsuario();
				perfilUsuario.setUsuario(new Usuario());
				perfilUsuario.getUsuario().setId(idUsuario);
				perfilUsuario.setPerfil(perfil);
				this.insert(perfilUsuario);
			}
		} else {
			
			List<PerfilEnum> perfisAdicionados = this.perfisAdicionados(list, perfisSelecionados);
			for (PerfilEnum perfil : perfisAdicionados) {
				PerfilUsuario perfilUsuario = new PerfilUsuario();
				perfilUsuario.setUsuario(new Usuario());
				perfilUsuario.getUsuario().setId(idUsuario);
				perfilUsuario.setPerfil(perfil);
				this.insert(perfilUsuario);
			}
			
			List<PerfilUsuario> perfisRemovidos = this.perfisRemovidos(list, perfisSelecionados);
			for (PerfilUsuario perfilUsuario : perfisRemovidos) {
				this.delete(perfilUsuario.getId());
			}
		}
	}

	private List<PerfilEnum> perfisAdicionados(List<PerfilUsuario> list, PerfilEnum[] perfisSelecionados) {
		List<PerfilEnum> perfisAdicionados = new ArrayList<PerfilEnum>();
		for (PerfilEnum perfil : perfisSelecionados) {
			boolean isAdicionado = true;
			for (PerfilUsuario perfilUsuario : list) {
				if (perfilUsuario.getPerfil() == perfil) {
					isAdicionado = false;
				}
			}
			if (isAdicionado) {
				perfisAdicionados.add(perfil);
			}
		}
		return perfisAdicionados;
	}
	
	private List<PerfilUsuario> perfisRemovidos(List<PerfilUsuario> list, PerfilEnum[] perfisSelecionados) {
		List<PerfilUsuario> perfisRemovidos = new ArrayList<PerfilUsuario>();
		for (PerfilUsuario perfilUsuario : list) {
			boolean isRemovido = true;
			for (PerfilEnum perfil : perfisSelecionados) {
				if (perfilUsuario.getPerfil() == perfil) {
					isRemovido = false;
				}
			}
			if (isRemovido) {
				perfisRemovidos.add(perfilUsuario);
			}
		}
		return perfisRemovidos;
	}
}
