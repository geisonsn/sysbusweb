package br.com.gsn.sysbusweb.util.formatador;

import java.util.regex.Pattern;

public class PlacaFormatter implements Formatter {

	private final Pattern formatado = Pattern.compile("([a-zA-Z]{3})(\\d{4})");
	private final Pattern naoFormatado = Pattern.compile("([a-zA-Z]{3})-(\\d{4})");
	
	@Override
	public String format(String valor) {
		return formatado.matcher(valor).replaceAll("$1-$2");
	}

	@Override
	public String unformat(String valor) {
		return naoFormatado.matcher(valor).replaceAll("$1$2");
	}
	

}
