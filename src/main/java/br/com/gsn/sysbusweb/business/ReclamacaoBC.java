package br.com.gsn.sysbusweb.business;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoPorLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRankingDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRequestDTO;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.persistence.ReclamacaoDAO;
import br.com.gsn.sysbusweb.util.Dates;
import br.com.gsn.sysbusweb.util.Util;
import br.com.gsn.sysbusweb.util.formatador.PlacaFormatter;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class ReclamacaoBC extends DelegateCrud<Reclamacao, Long, ReclamacaoDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	@Transactional
	public void saveReclamacao(ReclamacaoRequestDTO reclamacaoDTO) throws ParseException {
		ModelMapper mapper = new ModelMapper();
		
		mapper.addMappings(new PropertyMap<ReclamacaoRequestDTO, Reclamacao>() {
			@Override
			protected void configure() {
				map().setId(null);
				map().setDataOcorrencia(null);
				map().setDataRegistro(null);
				map().setHora(null);
			}
		});
		
		Reclamacao reclamacao = mapper.map(reclamacaoDTO, Reclamacao.class);
		
		reclamacao.setDataOcorrencia(Dates.parse(reclamacaoDTO.getDataOcorrencia(), Dates.FORMAT_PT_BR_DATE));
		reclamacao.setDataRegistro(Dates.parse(reclamacaoDTO.getDataHoraRegistro(), Dates.FORMAT_PT_BR_DATE_HOUR));
		reclamacao.setHora(Dates.parse(reclamacaoDTO.getHora(), Dates.FORMAT_PT_BR_HOUR));
		
		String placa = Util.capitalize(reclamacaoDTO.getPlaca());
		placa = new PlacaFormatter().unformat(placa);
		
		reclamacao.setPlacaLinha(placa);
		reclamacao.setDescricao(Util.blankToNull(reclamacaoDTO.getDescricao()));
		
		this.insert(reclamacao);
	}
	
	/**
	 * Realiza a inclusão de uma reclamação para veículo lotado. 
	 * Esta reclamação é registrada quando o usuário informa que o ônibus está lotado no checkin.
	 * @param idLinha
	 * @param idUsuario
	 * @param dataHoraRegistro
	 */
	public void insertReclamacaoVeiculoLotado(Long idLinha, Long idUsuario, Date dataHoraRegistro) {
		Reclamacao reclamacao = new Reclamacao();
		reclamacao.setLinha(new Linha());
		reclamacao.getLinha().setId(idLinha);
		reclamacao.setObjetoReclamado(ObjetoReclamadoEnum.VEICULO);
		reclamacao.setDataOcorrencia(dataHoraRegistro);
		reclamacao.setHora(dataHoraRegistro);
		reclamacao.setUsuario(new Usuario());
		reclamacao.getUsuario().setId(idUsuario);
		reclamacao.setDataRegistro(dataHoraRegistro);
		
		OrigemReclamacao origemReclamacao = origemReclamacaoBC
			.getByObjetoReclamadoAndTipoReclamacao(ObjetoReclamadoEnum.VEICULO, "Excesso de lotação");
		
		reclamacao.setOrigemReclamacao(origemReclamacao);
		
		if (origemReclamacao != null) { //Para não dar falha se OrigemReclamacao for alterada
			insert(reclamacao);
		}
	}
	
	public List<Reclamacao> pesquisar(Date dataInicio, Date dataFim) {
		return getDelegate().findByPeriodo(dataInicio, dataFim);
	}
	
	public List<Reclamacao> listReclamacoesPorMes(int mes) {
		return getDelegate().findByMes(mes);
	}
	
	public List<ReclamacaoDTO> listReclamadosPorMes(int mes) {
		return getDelegate().listReclamadosPorMes(mes);
	}
	
	public List<ReclamacaoRankingDTO> listLinhasMaisReclamadas(Integer quantidade) {
		return getDelegate().listLinhasMaisReclamadas(quantidade);
	}
	
	public List<ReclamacaoPorLinhaDTO> listPrincipaisReclamacoesPorLinha(Long idLinha, Integer quantidade) {
		return getDelegate().listPrincipaisReclamacoesPorLinha(idLinha, quantidade);
	}

	public List<ReclamacaoDTO> listObjetosMaisReclamados(int mes) {
		return getDelegate().listObjetosMaisReclamados(mes);
	}

	public List<ReclamacaoDTO> listLinhasMaisReclamadas(int mes) {
		return getDelegate().listLinhasMaisReclamadas(mes);
	}

	public List<ReclamacaoDTO> listEmpresasMaisReclamadas(int mes) {
		return getDelegate().listEmpresasMaisReclamadas(mes);
	}

	public List<ReclamacaoDTO> listPrincipaisReclamacoes(int mes) {
		return getDelegate().listPrincipaisReclamacoes(mes);
	}
	
	public List<ReclamacaoDTO> listarHorarioComMaiorLotacao() {
		return getDelegate().listarHorarioComMaiorLotacao();
	}
	
	public List<ReclamacaoDTO> listarLinhasComMaisLotacao() {
		return getDelegate().listarLinhasComMaisLotacao();
	}

}
