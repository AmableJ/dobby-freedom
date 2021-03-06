package uo.sdi.business.util;

public class BusinessException extends Exception{

	/**
	 * Excepción de lógica de negocio.
	 */
	private static final long serialVersionUID = 1L;
	
	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}
