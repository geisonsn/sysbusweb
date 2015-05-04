package br.com.gsn.sysbusweb.view;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.com.gsn.sysbusweb.domain.enums.Mes;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.stereotype.ViewController;

@ViewController
public class GraficoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private PieChartModel pieModel = new PieChartModel();
 
    @PostConstruct
    public void init() {
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
     
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
    
    public PieChartModel getPieModel() {
    	return pieModel;
    }
 
    private BarChartModel initBarModel() {
    	
//    	<p:chart type="bar" model="#{graficoMB.barModel}" style="height:400px; width: 100%"/>
    	
        BarChartModel model = new BarChartModel();
 
//        ChartSeries boys = new ChartSeries();
//        boys.setLabel("Boys");
//        boys.set("2004", 120);
//        boys.set("2005", 100);
//        boys.set("2006", 44);
//        boys.set("2007", 150);
//        boys.set("2008", 25);
// 
//        ChartSeries girls = new ChartSeries();
//        girls.setLabel("Girls");
//        girls.set("2004", 52);
//        girls.set("2005", 60);
//        girls.set("2006", 110);
//        girls.set("2007", 135);
//        girls.set("2008", 120);
// 
//        model.addSeries(boys);
//        model.addSeries(girls);
        
        ChartSeries motorista = new ChartSeries();
        motorista.setLabel(ObjetoReclamadoEnum.MOTORISTA.getDescricao());
        motorista.set("Janeiro", 99);
//        motorista.set("Fevereiro", 30);
//        motorista.set("Março", 19);
//        motorista.set("Abril", 1);
        
        ChartSeries cobrador = new ChartSeries();
        cobrador.setLabel("Cobrador");
        cobrador.set("Janeiro", 59);
//        cobrador.set("Fevereiro", 39);
//        cobrador.set("Março", 4);
//        cobrador.set("Abril", 18);
        
        ChartSeries veiculo = new ChartSeries();
        veiculo.setLabel("Veículo");
        veiculo.set("Janeiro", 52);
//        veiculo.set("Fevereiro", 36);
//        veiculo.set("Março", 15);
//        veiculo.set("Abril", 12);
        
        ChartSeries outros = new ChartSeries();
        outros.setLabel("Outros");
        outros.set("Janeiro", 12);
//        outros.set("Fevereiro", 36);
//        outros.set("Março", 15);
//        outros.set("Abril", 12);
        
        ChartSeries fiscal = new ChartSeries();
        fiscal.setLabel("Fiscal");
        fiscal.set("Janeiro", 0);
//        fiscal.set("Fevereiro", 36);
//        fiscal.set("Março", 15);
//        fiscal.set("Abril", 12);
        
        model.addSeries(motorista);
        model.addSeries(cobrador);
        model.addSeries(veiculo);
        model.addSeries(outros);
        model.addSeries(fiscal);
        
        model.setAnimate(true);
//        model.setStacked(true);
        model.setShowPointLabels(true);
        model.setMouseoverHighlight(false);
        return model;
    }
     
    private void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
//        createPieModel();
    }
     
    public void createPieModel() {
		pieModel = initPieModel();
		pieModel.setTitle("Reclamações por objeto reclado");
		pieModel.setLegendPosition("e");
//		pieModel.setSeriesColors("000, ccc, f00, 00f");
		pieModel.setShowDataLabels(true);
	}

	private PieChartModel initPieModel() {
		pieModel = new PieChartModel();
		
		
		List<ReclamacaoDTO> reclamados = reclamacaoBC.listReclamadosPorMes(Calendar.getInstance().get(Calendar.MONTH) + 1);
		for (ReclamacaoDTO reclamacao : reclamados) {
			pieModel.set(reclamacao.getObjetoReclamado(), reclamacao.getTotalReclamacoes());
		}
		
//		pieModel.set("Motorista", 20);
//		pieModel.set("Cobrador", 50);
//		pieModel.set("Veículo", 20);
//		pieModel.set("Outros", 10);
		return pieModel;
	}

	private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Reclamações mensais");
//        barModel.setLegendPosition("ne");
        barModel.setLegendPosition("ne");
        barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
         
        Axis xAxis = barModel.getAxis(AxisType.X);
//        xAxis.setLabel("Gender");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
//        yAxis.setLabel("Births");
        yAxis.setMin(0);
//        yAxis.setMax(60);
        yAxis.setMax(110);
    }
     
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);
 
        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);
         
        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Births");
        xAxis.setMin(0);
        xAxis.setMax(200);
         
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Gender");        
    }
    
    public PieChartModel gerarGraficoMensal() {
		pieModel = new PieChartModel();
		
		pieModel.setLegendPosition("e");
		pieModel.setShowDataLabels(true);
		
		int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
		List<ReclamacaoDTO> reclamados = reclamacaoBC.listReclamadosPorMes(mesAtual);
		for (ReclamacaoDTO reclamacao : reclamados) {
			pieModel.set(reclamacao.getObjetoReclamado(), reclamacao.getTotalReclamacoes());
		}
		
		return pieModel;
	}
    
    public String getMesCorrente() {
    	return Mes.getFromOrdinal(Calendar.getInstance().get(Calendar.MONTH)).getDescricao();
    }

}
