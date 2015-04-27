package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Terminal;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class TerminalDAO extends JPACrud<Terminal, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Terminal> findByDescricao(String descricao) {
		return (List<Terminal>)getEntityManager()
				.createNamedQuery("Terminal.findByDescricao")
				.setParameter("descricao", "%" + descricao.toUpperCase() + "%")
				.getResultList();
	}

}
