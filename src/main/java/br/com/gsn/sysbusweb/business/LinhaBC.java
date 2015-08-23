
package br.com.gsn.sysbusweb.business;

import java.util.Collections;
import java.util.List;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.dto.LinhaDTO;
import br.com.gsn.sysbusweb.persistence.LinhaDAO;
import br.com.gsn.sysbusweb.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LinhaBC extends DelegateCrud<Linha, Long, LinhaDAO> {
	private static final long serialVersionUID = 1L;

	public List<Linha> findByDescricaoByNumero(String descricao, String numero) {
		return getDelegate().findByDescricaoByNumero(descricao, numero);
	}
	
	public List<LinhaDTO> listAll() {
		List<Linha> linhas = getDelegate().findAll();
		
		if (linhas.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(linhas, LinhaDTO.class);
	}
	
	public List<LinhaDTO> findByNumeroLinha(String numeroLinha) {
		List<Linha> linhas = getDelegate().findByNumeroLinha(numeroLinha);
		
		if (linhas.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(linhas, LinhaDTO.class);
	}
}
