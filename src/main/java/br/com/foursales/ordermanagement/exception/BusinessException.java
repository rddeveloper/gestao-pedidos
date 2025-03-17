package br.com.foursales.ordermanagement.exception;

public class BusinessException extends RuntimeException {

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable throwable) {
		super(message, throwable);

	}
}
