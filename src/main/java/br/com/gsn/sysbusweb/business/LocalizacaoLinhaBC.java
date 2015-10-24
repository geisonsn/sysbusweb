package br.com.gsn.sysbusweb.business;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaInsertDTO;
import br.com.gsn.sysbusweb.persistence.LocalizacaoLinhaDAO;
import br.com.gsn.sysbusweb.util.Dates;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LocalizacaoLinhaBC extends	DelegateCrud<LocalizacaoLinha, Long, LocalizacaoLinhaDAO> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	public void insert(LocalizacaoLinhaInsertDTO ll) throws ParseException {
		
		Long idUsuario = ll.getIdUsuario();
		Date dataHoraRegistro = Dates.parse(ll.getDataHoraRegistro(), Dates.FORMAT_PT_BR_DATE_HOUR);
		
		LocalizacaoLinha linha = new LocalizacaoLinha();
		linha.setLinha(new Linha());
		linha.getLinha().setId(ll.getIdLinha());
		linha.setVeiculo(new Veiculo());
		linha.getVeiculo().setId(ll.getIdVeiculo());
		linha.setUsuario(new Usuario());
		linha.getUsuario().setId(idUsuario);
		linha.setLocataoVeiculo(ll.getLotacaoVeiculo());
		linha.setStatus(ll.getStatus());
		linha.setLatitude(ll.getLatitude());
		linha.setLongitude(ll.getLongitude());
		linha.setDataHoraRegistro(dataHoraRegistro);
		insert(linha);
		
		if (StringUtils.isNotEmpty(ll.getLotacaoVeiculo()) && ll.getLotacaoVeiculo().equals("3")) {
			reclamacaoBC.insertReclamacaoVeiculoLotado(ll.getIdLinha(), idUsuario, dataHoraRegistro);
		}
	}
	
	public List<LocalizacaoLinhaDTO> listUltimaLocalizacaoVeiculoDaLinha(String numeroLinha) {
		return getDelegate().listUltimaLocalizacaoVeiculoDaLinha(numeroLinha);
	}
	
	public List<LocalizacaoLinhaDTO> listVeiculosEmDeslocamento(Integer intervalo) {
		return getDelegate().listVeiculosEmDeslocamento(intervalo);
	}
	
	public List<LocalizacaoLinhaDTO> listVeiculosEmDeslocamentoProximos(Integer intervalo, Integer distancia, 
			String latitudeUsuario, String longitudeUsuario) {
		return getDelegate().listVeiculosEmDeslocamentoProximos(intervalo, distancia, latitudeUsuario, longitudeUsuario);
	}

}
