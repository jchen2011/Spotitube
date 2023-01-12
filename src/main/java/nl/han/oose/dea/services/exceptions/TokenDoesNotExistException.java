package nl.han.oose.dea.services.exceptions;

public class TokenDoesNotExistException extends RuntimeException {
    public TokenDoesNotExistException(String message) {
        super(message);
    }
}
