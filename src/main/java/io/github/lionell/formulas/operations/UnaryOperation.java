package io.github.lionell.formulas.operations;

import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.LogicalOperation;

import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
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
