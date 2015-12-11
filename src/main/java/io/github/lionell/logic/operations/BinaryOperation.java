package io.github.lionell.logic.operations;

import io.github.lionell.logic.Formula;
import io.github.lionell.logic.LogicalOperation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lionell on 07.12.2015.
 */
public abstract class BinaryOperation extends LogicalOperation {
    protected Formula left;
    protected Formula right;

    @Override
    public void rename(String from, String to) {
        left.rename(from, to);
        right.rename(from, to);
    }

    @Override
    public Set<String> getFreeVariableNames() {
        Set<String> freeVariableNames =
                new HashSet<>(left.getFreeVariableNames());
        freeVariableNames.addAll(right.getFreeVariableNames());
        return freeVariableNames;
    }
}
