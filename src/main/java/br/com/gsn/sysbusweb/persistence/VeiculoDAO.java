package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Veiculo;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class VeiculoDAO extends JPACrud<Veiculo, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> findByLinhaByNumero(String linha, String numeroRegistro) {
		return (List<Veiculo>)getEntityManager()
				.createNamedQuery(Veiculo.FIND_BY_LINHA_BY_REGISTRO)
				.setParameter("linha", "%" + linha + "%")
				.setParameter("numeroRegistro", "%" + numeroRegistro + "%")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Veiculo> findAll() {
		return getEntityManager().createNamedQuery("Veiculo.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> findByNumeroLinha(String numeroLinha) {
		return ((List<Veiculo>)getEntityManager()
			.createNamedQuery(Veiculo.FIND_BY_NUMERO_LINHA)
			.setParameter("numeroLinha", numeroLinha)
			.getResultList());
	}
	
	public Veiculo getByNumeroRegistro(String numeroRegistro) {
		return getEntityManager()
				.createNamedQuery(Veiculo.GET_BY_NUMERO_REGISTRO, Veiculo.class)
				.setParameter("numeroRegistro", numeroRegistro)
				.getSingleResult();
	}

}
