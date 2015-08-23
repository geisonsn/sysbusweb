package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import org.hibernate.criterion.MatchMode;

import br.com.gsn.sysbusweb.domain.Linha;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class LinhaDAO extends JPACrud<Linha, Long> {
	
	private static final long serialVersionUID = 1L;
	
	public List<Linha> findByDescricaoByNumero(String descricao, String numero) {
		return getEntityManager()
				.createNamedQuery(Linha.FIND_BY_DESCRICAO_BY_NUMERO, Linha.class)
				.setParameter("descricao", "%" + descricao.toUpperCase() + "%")
				.setParameter("numero", "%" + numero.toUpperCase() + "%")
				.getResultList();
	}
	
	@Override
	public List<Linha> findAll() {
		return getEntityManager().createNamedQuery(Linha.FIND_ALL, Linha.class).getResultList();
	}
	
	public List<Linha> findByNumeroLinha(String numeroLinha) {
		return getEntityManager()
				.createNamedQuery(Linha.FIND_BY_NUMERO, Linha.class)
				.setParameter("numero", MatchMode.START.toMatchString(numeroLinha))
				.getResultList();
	}

}
