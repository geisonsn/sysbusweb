package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Veiculo;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class VeiculoDAO extends JPACrud<Veiculo, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<Veiculo> findAll() {
		return getEntityManager().createNamedQuery(Veiculo.FIND_ALL, Veiculo.class).getResultList();
	}
	
	public Veiculo getByNumeroRegistro(String numeroRegistro) {
		return getEntityManager()
				.createNamedQuery(Veiculo.GET_BY_NUMERO_REGISTRO, Veiculo.class)
				.setParameter("numeroRegistro", numeroRegistro)
				.getSingleResult();
	}
	
	public Veiculo getByPlaca(String placa) {
		return getEntityManager()
				.createNamedQuery(Veiculo.GET_BY_PLACA, Veiculo.class)
				.setParameter("placa", placa)
				.getSingleResult();
	}
	
	public List<Veiculo> getByNumeroRegistroOuPlaca(String numeroRegistro, String placa) {
		return getEntityManager()
				.createNamedQuery(Veiculo.FIND_BY_REGISTRO_OU_PLACA, Veiculo.class)
				.setParameter("numeroRegistro", numeroRegistro)
				.setParameter("placa", placa)
				.getResultList();
	}
	
	public List<Veiculo> getByNumeroRegistroOuPlacaComExclusao(Long idVeiculoLinha, String numeroRegistro, String placa) {
		
		StringBuffer jpql = new StringBuffer()
			.append(" SELECT vl.veiculo FROM VeiculoLinha vl ")
			.append(" WHERE vl.id != :idVeiculoLinha ")
			.append(" and (vl.veiculo.numeroRegistro = :numeroRegistro or vl.veiculo.placa = :placa) ");
		
		return getEntityManager()
			.createQuery(jpql.toString(), Veiculo.class)
			.setParameter("idVeiculoLinha", idVeiculoLinha)
			.setParameter("numeroRegistro", numeroRegistro)
			.setParameter("placa", placa)
			.getResultList();
	}

}
