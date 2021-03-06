package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class TipoReclamacaoDAO extends JPACrud<TipoReclamacao, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<TipoReclamacao> findByDescricao(String descricao) {
		return (List<TipoReclamacao>)getEntityManager()
				.createNamedQuery("TipoReclamacao.findByDescricao")
				.setParameter("descricao", "%" + descricao.toUpperCase() + "%")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoReclamacao> listTipoReclamacaoCadastradasAoObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return (List<TipoReclamacao>)getEntityManager()
				.createNamedQuery("TipoReclamacao.cadastradasAoObjetoReclamado")
				.setParameter("objetoReclamado", objetoReclamado)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoReclamacao> listTipoReclamacaoNaoCadastradasAoObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return (List<TipoReclamacao>)getEntityManager()
				.createNamedQuery("TipoReclamacao.naoCadastradasAoObjetoReclamado")
				.setParameter("objetoReclamado", objetoReclamado)
				.getResultList();
	}
	

}
