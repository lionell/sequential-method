package io.github.lionell.formulas.operations;

import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.Operation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public abstract class BinaryOperation extends Operation {
    protected Formula left;
    protected Formula right;

    @Override
    public void rename(String from, String to) {
        left.rename(from, to);
        right.rename(from, to);
    }

    @Override
    public Set<String> getFreeVariableNames() {
        Set<String> names = new HashSet<>(left.getFreeVariableNames());
        names.addAll(right.getFreeVariableNames());
        return names;
    }

    @Override
    public Set<String> getVariableNames() {
        Set<String> names = new HashSet<>(left.getVariableNames());
        names.addAll(right.getVariableNames());
        return names;
    }
}
