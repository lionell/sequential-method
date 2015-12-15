package io.github.lionell.exceptions;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class LogicalException extends SystemException {
    public LogicalException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "[LE]: " + super.getMessage();
    }
}
