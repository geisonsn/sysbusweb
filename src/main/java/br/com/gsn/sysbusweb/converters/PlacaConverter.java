package br.com.gsn.sysbusweb.converters;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

@FacesConverter(value= "ConversorPlaca")
public class PlacaConverter implements Converter {
	
	//TODO criador formatador
	private Pattern format = Pattern.compile("([a-zA-Z]{3})-(\\d{4})");
	private Pattern unformat = Pattern.compile("([a-zA-Z]{3})(\\d{4})");
			
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (StringUtils.isBlank(value)) {
			return null;
		}
		
		return isPlacaValida(value) ? format.matcher(value).replaceAll("$1$2") : null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		String placa = (value == null ? null : value.toString());
		if (StringUtils.isBlank(placa)) {
			return "";
		}
		return unformat.matcher(placa).replaceAll("$1-$2");
	}
	
	private boolean isPlacaValida(String valor) {
		return format.matcher(valor).matches();
	}
	
	/*public static void main(String args[]) {
		String placaFormatada = "OAq-5555";
		String placaDesformatda = "OAL5555";
		Pattern format = Pattern.compile("([a-zA-Z]{3})-(\\d{4})");
		Pattern unformat = Pattern.compile("([a-zA-Z]{3})(\\d{4})");
		System.out.println("Casou: " + format.matcher(placaFormatada).matches());
		System.out.println("Placa formatada: " + unformat.matcher(placaDesformatda).replaceAll("$1-$2"));
		System.out.println("Placa desformatada: " + format.matcher(placaFormatada).replaceAll("$1$2"));
	}*/

}
