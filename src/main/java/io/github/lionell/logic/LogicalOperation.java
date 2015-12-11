package io.github.lionell.logic;

/**
 * Created by lionell on 07.12.2015.
 */
public abstract class LogicalOperation extends Formula {
    @Override
    public boolean isAtomic() {
        return false;
    }
}
