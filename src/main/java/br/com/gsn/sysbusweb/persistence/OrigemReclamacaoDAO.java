package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class OrigemReclamacaoDAO extends JPACrud<OrigemReclamacao, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		
		return (List<OrigemReclamacao>)getEntityManager()
				.createNamedQuery(OrigemReclamacao.FIND_BY_OBJETO_RECLAMADO)
				.setParameter("objetoReclamado", objetoReclamado)
				.getResultList();
	}
	
	public OrigemReclamacao getByObjetoReclamadoAndTipoReclamacao(ObjetoReclamadoEnum objetoReclamado, String tipoReclamacao) {
		
		StringBuffer jpql = new StringBuffer();
		jpql
			.append(" SELECT o FROM OrigemReclamacao o ")
			.append(" WHERE o.objetoReclamado = :objetoReclamado ")
			.append(" and o.tipoReclamacao.descricao = :tipoReclamacao ");
		
		return getEntityManager()
			.createQuery(jpql.toString(), OrigemReclamacao.class)
			.setParameter("objetoReclamado", objetoReclamado)
			.setParameter("tipoReclamacao", tipoReclamacao)
			.getSingleResult();
	}
	
	public int removeByTipoReclamacao(ObjetoReclamadoEnum objetoReclamado, List<Long> ids) {
		Query query = getEntityManager().createQuery("delete from OrigemReclamacao o where o.tipoReclamacao.id in (:ids) "
				+ "and o.objetoReclamado = :objetoReclamado");
		query.setParameter("ids", ids);
		query.setParameter("objetoReclamado", objetoReclamado);
		return query.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<OrigemReclamacao> findAll() {
		return getEntityManager().createNamedQuery(OrigemReclamacao.FIND_ALL).getResultList();
	}
}
