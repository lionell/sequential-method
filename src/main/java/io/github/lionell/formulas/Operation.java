package io.github.lionell.formulas;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public abstract class Operation extends Formula {
    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
