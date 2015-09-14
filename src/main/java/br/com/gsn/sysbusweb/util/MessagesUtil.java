package br.com.gsn.sysbusweb.util;

import java.io.Serializable;

import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

@Named
public class MessagesUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	@Name("ValidationMessages")
	private Instance<ResourceBundle> bundleValidation;
	
	@Inject
	@Name("messages")
	private Instance<ResourceBundle> bundleMessages;
	
	public String getMessage(String key) {
		return bundleValidation.get().getString(key);
	}
	
	public void exibirMensagemDuplicado() {
		final String titulo = this.getMessage("org.hibernate.validator.constraints.NotDuplicate.title");
		final String msg = this.getMessage("org.hibernate.validator.constraints.NotDuplicate");
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void exibirMensagem(String titulo, Exception e) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, e.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public void exibirMensagemCampoObrigatorio() {
		final String titulo = this.getMessage("org.hibernate.validator.constraints.NotBlank.title");
		final String msg = this.getMessage("org.hibernate.validator.constraints.NotBlank.message");
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	
}
