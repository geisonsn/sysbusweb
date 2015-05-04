package br.com.gsn.sysbusweb.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;

@FacesValidator("ObjetoReclamadoValidator")
public class ObjetoReclamadoValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		if (value == null || (value instanceof ObjetoReclamadoEnum && value.equals(ObjetoReclamadoEnum.SELECIONE))) {
			FacesMessage msg = new FacesMessage("Campo obrigatório.", 
				"Não pode ser nulo");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
		
	}

}
