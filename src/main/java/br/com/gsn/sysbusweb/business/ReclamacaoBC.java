package br.com.gsn.sysbusweb.business;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRankingDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRequestDTO;
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
	
	public List<Reclamacao> pesquisar(Date dataInicio, Date dataFim) {
		return getDelegate().findByPeriodo(dataInicio, dataFim);
	}
	
	public List<Reclamacao> listReclamacoesPorMes(int mes) {
		return getDelegate().findByMes(mes);
	}
	
	public List<ReclamacaoDTO> listReclamadosPorMes(int mes) {
		return getDelegate().listReclamadosPorMes(mes);
	}
	
	public List<ReclamacaoRankingDTO> listTopDezLinhasReclamadas() {
		return getDelegate().listTopDezLinhasReclamadas();
	}

	public List<ReclamacaoDTO> listObjetosMaisReclamados() {
		return getDelegate().listObjetosMaisReclamados();
	}

	public List<ReclamacaoDTO> listLinhasMaisReclamadas() {
		return getDelegate().listLinhasMaisReclamadas();
	}

	public List<ReclamacaoDTO> listEmpresasMaisReclamadas() {
		return getDelegate().listEmpresasMaisReclamadas();
	}

	public List<ReclamacaoDTO> listPrincipaisReclamacoes() {
		return getDelegate().listPrincipaisReclamacoes();
	}

}
