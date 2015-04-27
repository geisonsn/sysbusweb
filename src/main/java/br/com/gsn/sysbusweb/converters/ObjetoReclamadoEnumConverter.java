package br.com.gsn.sysbusweb.converters;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;

@FacesConverter(value = "ConversorObjetoReclamadoEnum")
public class ObjetoReclamadoEnumConverter extends EnumConverter {

	public ObjetoReclamadoEnumConverter() {
		super(ObjetoReclamadoEnum.class);
	}
}
