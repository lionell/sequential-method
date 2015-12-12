package io.github.lionell.miscellaneous;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public enum LogicalValue {
    TRUE, FALSE, UNKNOWN;

    public static LogicalValue negate(LogicalValue value) {
        if (value == UNKNOWN) {
            throw new IllegalArgumentException("Can't negate LogicalValue.UNKNOWN");
        }
        return value == TRUE ? FALSE : TRUE;
    }

    public Boolean toBoolean() {
        if (this == TRUE) {
            return true;
        } else if (this == FALSE) {
            return false;
        } else {
            return null;
        }
    }
}
