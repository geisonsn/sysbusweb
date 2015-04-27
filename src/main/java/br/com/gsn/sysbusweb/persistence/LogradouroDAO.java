package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.endereco.Logradouro;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class LogradouroDAO extends JPACrud<Logradouro, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Logradouro> findByNomeByBairro(String logradouro,  String bairro) {
		return (List<Logradouro>)getEntityManager()
				.createNamedQuery("Logradouro.findByNomeByBairro")
				.setParameter("logradouro", logradouro.toUpperCase() + "%")
				.setParameter("bairro", bairro.toUpperCase() + "%")
				.getResultList();
	}
}
