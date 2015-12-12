package io.github.lionell.formulas;

import io.github.lionell.formulas.Formula;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public abstract class LogicalOperation extends Formula {
    @Override
    public boolean isAtomic() {
        return false;
    }
}
