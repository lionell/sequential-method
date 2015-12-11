package io.github.lionell.logic;

import java.util.Set;

/**
 * Created by lionell on 07.12.2015.
 */
public abstract class Formula {
    protected LogicalValue value = LogicalValue.UNKNOWN;

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
