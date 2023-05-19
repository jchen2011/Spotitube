package nl.han.oose.dea.services.exceptions;

/**
 * Exception class for status 500 "INTERNAL SERVER ERROR".
 */
public class InternalServerError extends RuntimeException {

    public InternalServerError() {
        super("Please contact server administrators.");
    }
}

