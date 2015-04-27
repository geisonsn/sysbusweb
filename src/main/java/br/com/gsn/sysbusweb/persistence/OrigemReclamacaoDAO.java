package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class OrigemReclamacaoDAO extends JPACrud<OrigemReclamacao, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		
		StringBuffer sql = new StringBuffer(" select * from origem_reclamacao o where o.objeto_reclamado = :origemReclamacao ");
		
		return getEntityManager().createNativeQuery(sql.toString())
				.setParameter("origemReclamacao", objetoReclamado.name())
				.getResultList();
		
//		return (List<OrigemReclamacao>)getEntityManager()
//				.createNamedQuery("OrigemReclamacao.findByObjetoReclamado")
//				.setParameter("objetoReclamado", objetoReclamado.getDescricao())
//				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamado objetoReclamado) {
		
		return (List<OrigemReclamacao>)getEntityManager()
				.createNamedQuery("OrigemReclamacao.findByObjetoReclamado")
				.setParameter("objetoReclamado", objetoReclamado)
				.getResultList();
		
	}
}
