package br.com.gsn.sysbusweb.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

@FacesConverter(value= "ConversorHora")
public class HoraConverter implements Converter {

//	private static Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2})");
	DateFormat format = new SimpleDateFormat("HH:mm");
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (StringUtils.isBlank(value)) {
			return null;
		}
		
		try {
			return format.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if (value == null) {
			return null;
		}
		
		return format.format((Date)value);
	}
	
	/*public static void main(String[] args) throws ParseException {
		String hora = "22:00";
		String replacement = "$1$2";
		
		Date data = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		
		System.out.println(pattern.matcher(hora).matches());
		System.out.println(pattern.matcher(hora).replaceAll(replacement));
		System.out.println(pattern.matcher(hora).replaceAll(replacement));
		System.out.println(formato.parse(hora));
		System.out.println(formato.format(data));
		
	}*/

}
