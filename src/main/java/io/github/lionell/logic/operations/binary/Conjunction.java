package io.github.lionell.logic.operations.binary;

import io.github.lionell.logic.Formula;
import io.github.lionell.logic.LogicalValue;
import io.github.lionell.logic.Sequence;
import io.github.lionell.logic.operations.BinaryOperation;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Conjunction extends BinaryOperation {
    public Conjunction(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public Conjunction(Formula left, Formula right, LogicalValue value) {
        this(left, right);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        checkValue();
        if (value == LogicalValue.TRUE) {
            Sequence resultSequence = new Sequence(sigma);
            Formula newRight = right.clone();
            newRight.setValue(LogicalValue.TRUE);
            resultSequence.addFront(newRight);
            Formula newLeft = left.clone();
            newLeft.setValue(LogicalValue.TRUE);
            resultSequence.addFront(newLeft);
            return new Sequence[]{resultSequence};
        } else {
            Sequence firstBranch = new Sequence(sigma);
            Formula newLeft = left.clone();
            newLeft.setValue(LogicalValue.FALSE);
            firstBranch.addFront(newLeft);
            Sequence secondBranch = new Sequence(sigma);
            Formula newRight = right.clone();
            newRight.setValue(LogicalValue.FALSE);
            secondBranch.addFront(newRight);
            return new Sequence[]{firstBranch, secondBranch};
        }
    }

    @Override
    public Conjunction clone() {
        return new Conjunction(left.clone(), right.clone(), value);
    }

    @Override
    public String toString() {
        return "(" + right + ") && (" + left + ")";
    }
}
