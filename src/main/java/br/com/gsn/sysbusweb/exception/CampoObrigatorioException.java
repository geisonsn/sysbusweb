package br.com.gsn.sysbusweb.exception;

public class CampoObrigatorioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CampoObrigatorioException() {
		super();
	}
	
	public CampoObrigatorioException(String message) {
		super(message);
	}
}
