package exception;

public class CannotReadFileException extends RuntimeException {

    public CannotReadFileException(String message, Throwable e) {
        super(message, e);
    }

}
