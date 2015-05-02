package br.com.gsn.sysbusweb.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.util.Util;
import br.com.gsn.sysbusweb.util.formatador.PlacaFormatter.FormatoPlaca;

@FacesConverter(value= "ConversorPlaca")
public class PlacaConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (StringUtils.isBlank(value)) {
			return null;
		}
		
		return isPlacaValida(value) ? Util.desformatarPlaca(value) : null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		String placa = (value == null ? null : value.toString());
		if (StringUtils.isBlank(placa)) {
			return "";
		}
		return Util.formatarPlaca(placa);
	}
	
	private boolean isPlacaValida(String placa) {
		return Util.isPlacaValida(placa, FormatoPlaca.FORMATADO);
	}
	
}
