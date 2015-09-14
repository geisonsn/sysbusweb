package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.VeiculoLinha;

public class VeiculoLinhaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private VeiculoLinha veiculoLinha;
	
	public VeiculoLinhaDTO() {
		this.veiculoLinha = new VeiculoLinha();
		this.veiculoLinha.setVeiculo(new Veiculo());
	}

	public VeiculoLinhaDTO(VeiculoLinha veiculoLinha) {
		this.veiculoLinha = veiculoLinha;
		this.veiculoLinha.setVeiculo(veiculoLinha.getVeiculo());
	}
	
	public VeiculoLinha getVeiculoLinha() {
		return veiculoLinha;
	}
	
	@NotBlank
	public String getNumeroRegistro() {
		return veiculoLinha.getVeiculo().getNumeroRegistro();
	}
	
	public void setNumeroRegistro(String numeroRegistro) {
		veiculoLinha.getVeiculo().setNumeroRegistro(numeroRegistro);
	}
	
	public String getPlaca() {
		return veiculoLinha.getVeiculo().getPlaca();
	}
	
	public void setPlaca(String placa) {
		veiculoLinha.getVeiculo().setPlaca(placa);
	}

	public Linha getLinha() {
		return veiculoLinha.getLinha();
	}

	public void setLinha(Linha linha) {
		veiculoLinha.setLinha(linha);
	}
	
}
