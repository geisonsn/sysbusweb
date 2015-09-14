package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.VeiculoLinha;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class VeiculoLinhaDAO extends JPACrud<VeiculoLinha, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<VeiculoLinha> findByLinhaByNumero(String linha, String numeroRegistro) {
		return (List<VeiculoLinha>)getEntityManager()
				.createNamedQuery(VeiculoLinha.FIND_BY_LINHA_BY_REGISTRO)
				.setParameter("linha", "%" + linha + "%")
				.setParameter("numeroRegistro", "%" + numeroRegistro + "%")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VeiculoLinha> findAll() {
		return getEntityManager().createNamedQuery(VeiculoLinha.FIND_ALL).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<VeiculoLinha> findByNumeroLinha(String numeroLinha) {
		return ((List<VeiculoLinha>)getEntityManager()
			.createNamedQuery(VeiculoLinha.FIND_BY_NUMERO_LINHA)
			.setParameter("numeroLinha", numeroLinha)
			.getResultList());
	}
	
	public VeiculoLinha getByNumeroLinhaByNumeroRegistro(String numeroLinha, String numeroRegistro) {
		return getEntityManager()
			.createNamedQuery(VeiculoLinha.GET_BY_NUMERO_LINHA_BY_NUMERO_REGISTRO, VeiculoLinha.class)
			.setParameter("numero", numeroLinha)
			.setParameter("numeroRegistro", numeroRegistro)
			.getSingleResult();
	}
	
	public List<VeiculoLinha> listByNumeroLinhaByNumeroRegistroExcludente(Long id, String numeroLinha, String numeroRegistro, String placa) {
		
		StringBuffer jpql = new StringBuffer()
			.append("select v from VeiculoLinha v ")
			.append(" where v.id != :id ")
			.append(" and v.linha.numero = :numeroLinha ")
			.append(" and (v.veiculo.numeroRegistro = :numeroRegistro or v.veiculo.placa = :placa)");
		
		return getEntityManager()
			.createQuery(jpql.toString(), VeiculoLinha.class)
			.setParameter("id", id)
			.setParameter("numeroLinha", numeroLinha)
			.setParameter("numeroRegistro", numeroRegistro)
			.setParameter("placa", placa)
			.getResultList();
	}
	
	public int removeByLinha(Linha linha, List<Long> ids) {
		Query query = getEntityManager().createQuery("delete from VeiculoLinha v where v.veiculo.id in (:ids) "
				+ "and v.linha = :linha");
		query.setParameter("ids", ids);
		query.setParameter("linha", linha);
		return query.executeUpdate();
	}
	
}
