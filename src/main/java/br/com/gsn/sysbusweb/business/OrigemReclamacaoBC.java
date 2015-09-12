
package br.com.gsn.sysbusweb.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.com.gsn.sysbusweb.domain.dto.OrigemReclamacaoDTO;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.persistence.OrigemReclamacaoDAO;
import br.com.gsn.sysbusweb.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class OrigemReclamacaoBC extends DelegateCrud<OrigemReclamacao, Long, OrigemReclamacaoDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoReclamacaoBC tipoReclamacaoBC;
	
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return getDelegate().findByObjetoReclamado(objetoReclamado);
	}
	
	public List<OrigemReclamacao> carregarComboByObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		List<OrigemReclamacao> lista = getDelegate().findByObjetoReclamado(objetoReclamado);
		return ordenar(lista);
	}
	
	private List<OrigemReclamacao> ordenar(List<OrigemReclamacao> lista) {
		List<OrigemReclamacao> listaRetorno = new ArrayList<OrigemReclamacao>();
		OrigemReclamacao outros = null;
		for (OrigemReclamacao r : lista) {
			if (r.getTipoReclamacao().getDescricao().equalsIgnoreCase("Outros") || 
					r.getTipoReclamacao().getDescricao().equalsIgnoreCase("Outras")) {
				outros = r;
				continue;
			}
			listaRetorno.add(r);
		}
		if (outros != null) {
			listaRetorno.add(outros);
		}
		return listaRetorno;
	}
	
	@Deprecated
	public List<OrigemReclamacaoDTO> findByObjetoReclamado(String objetoReclamado) {
		
		List<OrigemReclamacao> listOrigemReclamacao = getDelegate()
			.findByObjetoReclamado(ObjetoReclamadoEnum.getFromName(objetoReclamado));
		
		if (listOrigemReclamacao.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(listOrigemReclamacao, OrigemReclamacaoDTO.class, 
					new PropertyMap<OrigemReclamacao, OrigemReclamacaoDTO>() {
			@Override
			protected void configure() {
				map().setIdOrigemReclamacao(source.getId());
				map().setIdTipoReclamacao(source.getTipoReclamacao().getId());
			}
		});
	}
	
	public List<OrigemReclamacaoDTO> carregarComboByObjetoReclamado(String objetoReclamado) {
		
		List<OrigemReclamacao> listOrigemReclamacao = this
				.carregarComboByObjetoReclamado(ObjetoReclamadoEnum.getFromName(objetoReclamado));
		
		if (listOrigemReclamacao.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(listOrigemReclamacao, OrigemReclamacaoDTO.class, 
				new PropertyMap<OrigemReclamacao, OrigemReclamacaoDTO>() {
			@Override
			protected void configure() {
				map().setIdOrigemReclamacao(source.getId());
				map().setIdTipoReclamacao(source.getTipoReclamacao().getId());
			}
		});
	}
	
	public void salvarReclamacoes(ObjetoReclamadoEnum objetoReclamado, List<TipoReclamacao> reclamacoesSelecionadas) {
		
		List<TipoReclamacao> reclamacoesCadastradas = tipoReclamacaoBC
			.listTipoReclamacaoCadastradasAoObjetoReclamado(objetoReclamado);
		
		List<TipoReclamacao> novas = this.recuperarReclamacoesIncluidas(reclamacoesCadastradas, 
			reclamacoesSelecionadas);
		
		this.salvarReclamacao(objetoReclamado, novas);
		
		List<Long> removidas = this.recuperarReclamacoesRemovidas(reclamacoesCadastradas, 
			reclamacoesSelecionadas);
		
		this.removerReclamacoes(objetoReclamado, removidas);
		
	}

	private void salvarReclamacao(ObjetoReclamadoEnum objetoReclamado,
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
			getDelegate().removeByTipoReclamacao(objetoReclamado, removidas);
		}
	}
	
	private List<TipoReclamacao> recuperarReclamacoesIncluidas(
			List<TipoReclamacao> reclamacoesCadastradas,
			List<TipoReclamacao> reclamacoesSelecionadas) {
		
		//Se ainda não existem reclamações cadastradas todas as selecionadas são elegíveis para a inserção
		if (reclamacoesCadastradas.isEmpty()) {
			return reclamacoesSelecionadas;
		}
		
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
		
		//Se ainda não existe reclamações cadastradas nenhuma remoção ocorrerá
		if (reclamacoesCadastradas.isEmpty()) {
			return Arrays.asList(ArrayUtils.EMPTY_LONG_OBJECT_ARRAY);
//			return new ArrayList<Long>();
		}
		
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
