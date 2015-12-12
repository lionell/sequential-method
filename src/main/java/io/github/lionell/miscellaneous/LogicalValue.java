package io.github.lionell.miscellaneous;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public enum LogicalValue {
    TRUE, FALSE, UNKNOWN;

    public LogicalValue negate() {
        if (this == UNKNOWN) {
            throw new IllegalArgumentException("Can't negate LogicalValue.UNKNOWN");
        }
        return this == TRUE ? FALSE : TRUE;
    }

    public Boolean toBoolean() {
        if (this == UNKNOWN) {
            return null;
        }
        return this == TRUE;
    }
}
