package compilers.exceptions;

public abstract class CompilerException extends ArgumentException {
    public CompilerException(String line, String message) {
        super(message + " at: \n" + line);
    }
}
