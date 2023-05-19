package nl.han.oose.dea.utils;

/**
 * Class for the logging of errors in a log.
 */
public class Log {

    private Log() {}

    /**
     * Method for the logging of errors.
     * Will print the message and error into the console.
     *
     * @param message The message as a {@link String}
     * @param e The error as a {@link Exception}
     */
    public static void console(String message, Exception e) {
        System.out.println(message);
        System.out.println(e);
    }
}
