package io.github.lionell.logic;

/**
 * Created by lionell on 07.12.2015.
 */
public enum LogicalValue {
    TRUE, FALSE, UNKNOWN;
    public static LogicalValue negate(LogicalValue value) {
        if (value == UNKNOWN) {
            throw new IllegalArgumentException("Can't negate LogicalValue.UNKNOWN");
        }
        return value == TRUE ? FALSE : TRUE;
    }
}
