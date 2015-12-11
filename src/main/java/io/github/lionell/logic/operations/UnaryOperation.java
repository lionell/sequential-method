package io.github.lionell.logic.operations;

import io.github.lionell.logic.Formula;
import io.github.lionell.logic.LogicalOperation;

import java.util.Set;

/**
 * Created by lionell on 07.12.2015.
 */
public abstract class UnaryOperation extends LogicalOperation {
    protected Formula formula;

    @Override
    public void rename(String from, String to) {
        formula.rename(from, to);
    }

    @Override
    public Set<String> getFreeVariableNames() {
        return formula.getFreeVariableNames();
    }
}
