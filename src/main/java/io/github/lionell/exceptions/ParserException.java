package io.github.lionell.exceptions;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class ParserException extends RuntimeException {
    public ParserException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "[PE]: " + super.getMessage();
    }
}
