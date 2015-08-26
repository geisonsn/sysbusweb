package br.com.gsn.sysbusweb.exception;

public class UsuarioExistenteException extends RuntimeException {

	public UsuarioExistenteException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
