package br.com.gsn.sysbusweb.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.com.gsn.sysbusweb.domain.enums.Mes;
import br.com.gsn.sysbusweb.util.Util;
import br.com.gsn.sysbusweb.util.Util.FormatoData;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class GestorMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	private Mes mes;
	
	private Date dataInicio, dataFim;

	private List<Reclamacao> listReclamacoesPorMes = new ArrayList<Reclamacao> ();
	
	private List<Reclamacao> listReclamacoesPorPeriodo = new ArrayList<Reclamacao> ();
	
	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<Reclamacao> getListReclamacoesPorMes() {
		if (listReclamacoesPorMes.isEmpty()) {
			pesquisarReclamacoesPorMes();
		}
		return listReclamacoesPorMes;
	}

	public void setListReclamacoesPorMes(List<Reclamacao> listReclamacoesPorMes) {
		this.listReclamacoesPorMes = listReclamacoesPorMes;
	}
	
	public List<Reclamacao> getListReclamacoesPorPeriodo() {
		if (listReclamacoesPorPeriodo.isEmpty()) {
			pesquisarReclamacoesPorPeriodo();
		}
		return listReclamacoesPorPeriodo;
	}

	public void setListReclamacoesPorPeriodo(
			List<Reclamacao> listReclamacoesPorPeriodo) {
		this.listReclamacoesPorPeriodo = listReclamacoesPorPeriodo;
	}

	public List<Mes> getComboMes() {
		return Mes.listToCurrentMonth();
	}
	
	public String getMesCorrente() {
    	return Mes.getFromOrdinal(Util.getMesCorrente()).getDescricao();
    }
	
	public Date getDataMaxima() {
		return new Date();
	}

	/**
	 * Lista os principais reclamados
	 * @return
	 */
	public List<ReclamacaoDTO> listObjetosMaisReclamados() {
		int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return reclamacaoBC.listObjetosMaisReclamados(mes);
	}

	/**
	 * Lista as linhas mais reclamadas
	 * @return
	 */
	public List<ReclamacaoDTO> listLinhasMaisReclamadas() {
		int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return reclamacaoBC.listLinhasMaisReclamadas(mes);
	}

	/**
	 * Lista as empresas mais reclamadas
	 * @return
	 */
	public List<ReclamacaoDTO> listEmpresasMaisReclamadas() {
		int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return reclamacaoBC.listEmpresasMaisReclamadas(mes);
	}

	/**
	 * Lista as principais reclamações registradas
	 * @return
	 */
	public List<ReclamacaoDTO> listPrincipaisReclamacoes() {
		int mes = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return reclamacaoBC.listPrincipaisReclamacoes(mes);
	}
	
	@PostConstruct
	private void init() {
		this.mes = Mes.getFromOrdinal(Util.getMesCorrente());
//		pesquisarReclamacoesPorMes();
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirRelatorioMensal() throws JRException, IOException {
		List<ReclamacaoDTO> list = this.transferDados(this.listReclamacoesPorMes);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String url = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/rel_reclamacao_mensal.jasper");
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
		HashMap parametros = new HashMap();
		parametros.put("mesPesquisado", this.mes.getDescricao());
		JasperPrint jasperPrint = JasperFillManager.fillReport(url, parametros, beanCollectionDataSource);
		
		response.addHeader("Content-disposition", "attachment; filename=relatorio_reclamacao_mensal.pdf");
		ServletOutputStream servletOutputStream= response.getOutputStream();
		
		try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirRelatorioPorPeriodo() throws JRException, IOException {
		List<ReclamacaoDTO> list = this.transferDados(this.listReclamacoesPorPeriodo);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String url = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/rel_reclamacao_periodo.jasper");
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
		HashMap parametros = new HashMap();
		parametros.put("periodoPesquisado", (Util.formatarData(this.dataInicio, FormatoData.Simples) + " a " 
				+ Util.formatarData(this.dataFim, FormatoData.Simples))) ;
		JasperPrint jasperPrint = JasperFillManager.fillReport(url, parametros, beanCollectionDataSource);
		
		response.addHeader("Content-disposition", "attachment; filename=rel_reclamacao_periodo.pdf");
		ServletOutputStream servletOutputStream= response.getOutputStream();
		
		try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private List<ReclamacaoDTO> transferDados(List<Reclamacao> listReclamacoes) {
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		for (Reclamacao reclamacao : listReclamacoes) {
			ReclamacaoDTO reclamacaoDTO = new ReclamacaoDTO();
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(new PropertyMap<Reclamacao, ReclamacaoDTO>() {
				@Override
				protected void configure() {
					map().setDataRegistro(source.getDataRegistroFormatada());
					map().setNumeroLinha(source.getLinha().getNumero());
					map().setObjetoReclamado(source.getObjetoReclamadoToString());
					map().setTipoReclamacao(source.getOrigemReclamacao().getTipoReclamacao().getDescricao());
					map().setDataOcorrencia(source.getDataOcorrenciaFormatada());
					map().setHoraOcorrencia(source.getHoraFormatada());
					map().setPlacaVeiculo(source.getPlacaFormatada());
					map().setDetalhesReclamacao(source.getDescricao());
				}
			});
			mapper.map(reclamacao, reclamacaoDTO);
			list.add(reclamacaoDTO);
		}
		return list;
	}

	public void pesquisarReclamacoesPorMes() {
		listReclamacoesPorMes = reclamacaoBC.listReclamacoesPorMes(mes.ordinal()+1);
	}
	
	public void pesquisarReclamacoesPorPeriodo() {
		
		if (dataInicio == null || dataFim == null) {
			this.listReclamacoesPorPeriodo = this.reclamacaoBC.findAll();
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataFim);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
					calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			
			this.dataFim = calendar.getTime();
			this.listReclamacoesPorPeriodo = this.reclamacaoBC.pesquisar(this.dataInicio, this.dataFim);
			
		}
	}
	
	public List<ReclamacaoDTO> listarLinhasComMaisLotacao() {
		return reclamacaoBC.listarLinhasComMaisLotacao();
	}
	
	public List<ReclamacaoDTO> listarHorarioComMaiorLotacao() {
		return reclamacaoBC.listarHorarioComMaiorLotacao();
	}
	
	
}
