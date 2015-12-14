package io.github.lionell.exceptions;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class ConverterException extends RuntimeException {
    public ConverterException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "[CE]: " + super.getMessage();
    }
}
