package br.com.foursales.ordermanagement.exception;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String message) {
        super(message);
    }
    public ServerErrorException(String message, Exception exception) {
        super(message, exception);
    }
}
