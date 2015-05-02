package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Linha;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class LinhaDAO extends JPACrud<Linha, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Linha> findByDescricaoByNumero(String descricao, String numero) {
		return (List<Linha>)getEntityManager()
				.createNamedQuery("Linha.findByDescricaoByNumero")
				.setParameter("descricao", "%" + descricao.toUpperCase() + "%")
				.setParameter("numero", "%" + numero.toUpperCase() + "%")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Linha> findAll() {
		return getEntityManager().createNamedQuery("Linha.findAll").getResultList();
	}

}
