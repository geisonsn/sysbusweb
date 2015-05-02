
package br.com.gsn.sysbusweb.business;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.persistence.OrigemReclamacaoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class OrigemReclamacaoBC extends DelegateCrud<OrigemReclamacao, Long, OrigemReclamacaoDAO> {
	private static final long serialVersionUID = 1L;
	
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return getDelegate().findByObjetoReclamado(objetoReclamado);
	}
	
	public void salvarReclamacoes2(ObjetoReclamadoEnum objetoReclamado,
			List<OrigemReclamacao> reclamacoesCadastradas,
			List<OrigemReclamacao> reclamacoesSelecionadas) {
		
		/*List<TipoReclamacao> novas = this.recuperarReclamacoesIncluidas(reclamacoesCadastradas, 
				reclamacoesSelecionadas);
		
		this.salvarReclamacoes(objetoReclamado, novas);
		
		List<Long> removidas = this.recuperarReclamacoesRemovidas(reclamacoesCadastradas, 
				reclamacoesSelecionadas);
		
		this.removerReclamacoes(objetoReclamado, removidas);*/
		
	}
	
	public void salvarReclamacoes(ObjetoReclamadoEnum objetoReclamado,
			List<TipoReclamacao> reclamacoesCadastradas,
			List<TipoReclamacao> reclamacoesSelecionadas) {
		
		List<TipoReclamacao> novas = this.recuperarReclamacoesIncluidas(reclamacoesCadastradas, 
				reclamacoesSelecionadas);
		
		this.salvarReclamacoes(objetoReclamado, novas);
		
		List<Long> removidas = this.recuperarReclamacoesRemovidas(reclamacoesCadastradas, 
				reclamacoesSelecionadas);
		
		this.removerReclamacoes(objetoReclamado, removidas);
		
	}

	private void salvarReclamacoes(ObjetoReclamadoEnum objetoReclamado,
			List<TipoReclamacao> novas) {
		
		for (TipoReclamacao tipoReclamacao : novas) {
			OrigemReclamacao origemReclamacao = new OrigemReclamacao();
			origemReclamacao.setTipoReclamacao(tipoReclamacao);
			origemReclamacao.setObjetoReclamado(objetoReclamado);
			this.insert(origemReclamacao);
		}
	}
	
	private void removerReclamacoes(ObjetoReclamadoEnum objetoReclamado,
			List<Long> removidas) {
		
		if (!removidas.isEmpty()) {
			getDelegate().removeByTipoReclamacao(removidas);
		}
	}
	
	private List<TipoReclamacao> recuperarReclamacoesIncluidas(
			List<TipoReclamacao> reclamacoesCadastradas,
			List<TipoReclamacao> reclamacoesSelecionadas) {
		
		List<TipoReclamacao> list = new ArrayList<TipoReclamacao>();
		
		for (TipoReclamacao selecionada : reclamacoesSelecionadas) {
			boolean isNova = true;
			for (TipoReclamacao cadastrada : reclamacoesCadastradas) {
				if (selecionada.equals(cadastrada)) {
					isNova = false;
					break;
				}
			}
			if (isNova) {
				list.add(selecionada);
			}
		}
		
		return list;
	}

	private List<Long> recuperarReclamacoesRemovidas(
			List<TipoReclamacao> reclamacoesCadastradas,
			List<TipoReclamacao> reclamacoesSelecionadas) {
		
		List<Long> list = new ArrayList<Long>();
		
		for (TipoReclamacao cadastrada : reclamacoesCadastradas) {
			boolean isRemovida = true;
			for (TipoReclamacao selecionada : reclamacoesSelecionadas) {
				if (cadastrada.equals(selecionada)) {
					isRemovida = false;
					break;
				}
			}
			if (isRemovida) {
				list.add(cadastrada.getId());
			}
		}
		
		return list;
	}
	
}
