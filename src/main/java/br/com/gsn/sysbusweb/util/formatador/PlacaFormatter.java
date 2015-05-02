package br.com.gsn.sysbusweb.util.formatador;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public final class PlacaFormatter implements Formatter {

	private final Pattern padraoPlacaDesformatada = Pattern.compile(FormatoPlaca.NAO_FORMATADO.padrao);
	private final Pattern padraoPlacaFormatada = Pattern.compile(FormatoPlaca.FORMATADO.padrao);
	
	@Override
	public String format(String placa) {
		
		if (StringUtils.isBlank(placa)) {
			return StringUtils.EMPTY;
		}
		
		return padraoPlacaDesformatada.matcher(placa).replaceAll("$1-$2");
	}

	@Override
	public String unformat(String placa) {
		
		if (StringUtils.isBlank(placa)) {
			return StringUtils.EMPTY;
		}
		
		return padraoPlacaFormatada.matcher(placa).replaceAll("$1$2");
	}
	
	public boolean isValid(String placa, FormatoPlaca formato) {
		return Pattern.compile(formato.padrao).matcher(placa).matches();
	}
	
	public enum FormatoPlaca {
		
		FORMATADO("([a-zA-Z]{3})-(\\d{4})"),
		NAO_FORMATADO("([a-zA-Z]{3})(\\d{4})");
		
		private String padrao;
		
		private FormatoPlaca(String padrao) {
			this.padrao = padrao;
		}
	}
	
}
