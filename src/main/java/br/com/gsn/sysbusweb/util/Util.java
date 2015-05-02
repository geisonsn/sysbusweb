package br.com.gsn.sysbusweb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.util.formatador.PlacaFormatter;
import br.com.gsn.sysbusweb.util.formatador.PlacaFormatter.FormatoPlaca;

public final class Util {
	
	private Util() {}
	
	public static String formatarData(Date data, FormatoData formato) {
		if (data == null) {
			return StringUtils.EMPTY;
		}
		return formato.format(data);
	}
	
	public static String formatarPlaca(String placa) {
		return formatadorDePlaca.format(placa);
	}
	
	public static String desformatarPlaca(String placa) {
		return formatadorDePlaca.unformat(placa);
	}
	
	public static boolean isPlacaValida(String placa, FormatoPlaca formato) {
		return formatadorDePlaca.isValid(placa, formato);
	}
	
	private static PlacaFormatter formatadorDePlaca = new PlacaFormatter();
	
	public enum FormatoData {
		Basico {
			@Override
			public String format(Date data) {
				return formatoDataBasica.format(data);
			}
		}, 
		Completo {
			@Override
			public String format(Date data) {
				return formatoDataCompleta.format(data);
			}
		};
		
		private static DateFormat formatoDataBasica = new SimpleDateFormat("dd/MM/yyyy");
		private static DateFormat formatoDataCompleta = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		public abstract String format(Date data);
	}
	
}
