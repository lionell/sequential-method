package io.github.lionell.exceptions;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class GeneratorException extends SystemException {
    public GeneratorException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "[GE]: " + super.getMessage();
    }
}
