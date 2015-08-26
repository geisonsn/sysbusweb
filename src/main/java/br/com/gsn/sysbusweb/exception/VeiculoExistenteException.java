package br.com.gsn.sysbusweb.exception;

public class VeiculoExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public VeiculoExistenteException(String message) {
		super(message);
	}
}
