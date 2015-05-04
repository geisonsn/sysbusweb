package br.com.gsn.sysbusweb.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.gsn.sysbusweb.annotation.ValidEnum;

public class EnumValidator implements ConstraintValidator<ValidEnum, String>{

	private ValidEnum annotation;
	
	@Override
	public void initialize(ValidEnum annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(String valueForValidation, ConstraintValidatorContext context) {
		
		boolean result = false;
        
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        
        if (enumValues != null) {
			/*for (int i = 1; i < enumValues.length; i++) {
				if (valueForValidation.equals(enumValues[i].toString())
						|| (this.annotation.ignoreCase() && valueForValidation
								.equalsIgnoreCase(enumValues[i].toString()))) {
					result = true;
					break;
				}
			}*/
        	
            for(Object enumValue : enumValues) {
            	
            	if (enumValue.toString().toLowerCase().equals("Selecione".toLowerCase())) {
            		return false;
            	}
            	
                if (valueForValidation.equals(enumValue.toString()) 
                   || (this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString()))) {
                    result = true; 
                    break;
                }
            }
        }
        
        return result;
		
	}

}
