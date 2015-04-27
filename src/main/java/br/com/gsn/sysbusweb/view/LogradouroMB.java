package br.com.gsn.sysbusweb.view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.business.LogradouroBC;
import br.com.gsn.sysbusweb.domain.endereco.Logradouro;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class LogradouroMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LogradouroBC logradouroBC;
	
	public List<Logradouro> findLogradouro(String logradouroPesquisado) {
		if (!StringUtils.isBlank(logradouroPesquisado)) {
			if (logradouroPesquisado.contains(",")) {
				String[] split = logradouroPesquisado.trim().split(",");
				String logradouro = split[0].trim();
				String bairro = split.length > 1 ? split[1].trim() : "";
				return logradouroBC.findByNomeByBairro(logradouro, bairro);
			}
		}
		return null;
	}

}
