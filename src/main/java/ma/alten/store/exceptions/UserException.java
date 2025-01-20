package ma.alten.store.exceptions;

public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
