package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Empresa;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class EmpresaDAO extends JPACrud<Empresa, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Empresa> findByNome(String nome) {
		return (List<Empresa>)getEntityManager()
				.createNamedQuery("Empresa.findByNome")
				.setParameter("nome", "%" + nome.toUpperCase() + "%")
				.getResultList();
	}

}
