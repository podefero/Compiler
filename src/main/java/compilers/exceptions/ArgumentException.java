package compilers.exceptions;

public class ArgumentException extends RuntimeException{
    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
