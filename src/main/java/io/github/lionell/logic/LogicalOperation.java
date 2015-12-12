package io.github.lionell.logic;

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
