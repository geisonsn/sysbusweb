package br.com.gsn.sysbusweb.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public final class Util {
	
	private Util() {}
	
	public static String formatarData(Date data, FormatoData formato) {
		if (data == null) {
			return StringUtils.EMPTY;
		}
		return formato.format(data);
	}
	
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
