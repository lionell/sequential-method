package io.github.lionell.logic;

import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public abstract class Formula {
    protected LogicalValue value = LogicalValue.UNKNOWN;

    public LogicalValue getValue() {
        return value;
    }

    public void setValue(LogicalValue value) {
        this.value = value;
    }

    public boolean isEvaluated() {
        return value != LogicalValue.UNKNOWN;
    }

    protected void checkValue() {
        if (!isEvaluated()) {
            throw new IllegalStateException("Formula is not evaluated!");
        }
    }

    public abstract Formula clone();

    public abstract boolean isAtomic();

    public abstract Sequence[] expand(Sequence sigma);

    public abstract void rename(String from, String to);

    public abstract Set<String> getFreeVariableNames();
}
