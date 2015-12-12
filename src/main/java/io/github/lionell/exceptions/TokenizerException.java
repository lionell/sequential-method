package io.github.lionell.exceptions;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class TokenizerException extends RuntimeException {
    public TokenizerException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "[TE]: " + super.getMessage();
    }
}
