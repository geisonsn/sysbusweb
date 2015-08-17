package br.com.gsn.sysbusweb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.util.formatador.PlacaFormatter;
import br.com.gsn.sysbusweb.util.formatador.PlacaFormatter.FormatoPlaca;

public final class Util {
	
	private Util() {}
	
	public static String blankToNull(String string) {
		if (StringUtils.isBlank(string)) {
			return null;
		}
		return string;
	}
	
	public static String capitalize(String string) {
		if (StringUtils.isBlank(string)) {
			return null;
		}
		return string.toUpperCase();
	}
	
	public static String uncapitalize(String string) {
		
		if (StringUtils.isBlank(string)) {
			return null;
		}
		
		return string.toLowerCase();
	}
	
	public static int getMesCorrente() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	
	public static String formatarData(Date data, FormatoData formato) {
		if (data == null) {
			return StringUtils.EMPTY;
		}
		return formato.format(data);
	}
	
	public static String formatarHora(Date data, FormatoHora formato) {
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
		Simples {
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
		private static DateFormat formatoDataCompleta = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		public abstract String format(Date data);
	}
	
	public enum FormatoHora {
		
		Simples {
			@Override
			String format(Date data) {
				return formatoHoraSimples.format(data);
			}
		},
		Completo {
			@Override
			String format(Date data) {
				return formatoHoraCompleta.format(data);
			}
		};

		private static DateFormat formatoHoraSimples = new SimpleDateFormat("HH:mm");
		private static DateFormat formatoHoraCompleta = new SimpleDateFormat("HH:mm:ss");
		
		abstract String format(Date data);
		
	}
	
}
