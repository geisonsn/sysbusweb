package br.com.gsn.sysbusweb.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class GestorMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamacaoBC reclamacaoBC;

	public List<ReclamacaoDTO> listObjetosMaisReclamados() {
		return reclamacaoBC.listObjetosMaisReclamados();
	}

	public List<ReclamacaoDTO> listLinhasMaisReclamadas() {
		return reclamacaoBC.listLinhasMaisReclamadas();
	}

	public List<ReclamacaoDTO> listEmpresasMaisReclamadas() {
		return reclamacaoBC.listEmpresasMaisReclamadas();
	}

	public List<ReclamacaoDTO> listPrincipaisReclamacoes() {
		return reclamacaoBC.listPrincipaisReclamacoes();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirRelatorio() throws JRException, IOException {
		List<ReclamacaoDTO> list = this.listPrincipaisReclamacoes();
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String url = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/rel_mensal_reclamacao.jasper");
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
		JasperPrint jasperPrint = JasperFillManager.fillReport(url, new HashMap(), beanCollectionDataSource);
		
		response.addHeader("Content-disposition", "attachment; filename=rel_mensal_reclamacao.pdf");
		ServletOutputStream servletOutputStream= response.getOutputStream();
		
		try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
