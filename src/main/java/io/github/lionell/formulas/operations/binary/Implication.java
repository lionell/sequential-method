package io.github.lionell.formulas.operations.binary;

import io.github.lionell.containers.Sequence;
import io.github.lionell.exceptions.LogicalException;
import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.operations.BinaryOperation;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.utils.StringUtils;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Implication extends BinaryOperation {
    public Implication(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public Implication(Formula left, Formula right, LogicalValue value) {
        this(left, right);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        if (!isEvaluated()) {
            throw new LogicalException("Can't expand not evaluated implication!");
        }
        if (value == LogicalValue.TRUE) {
            Sequence firstBranch = new Sequence(sigma);
            Formula newLeft = left.clone();
            newLeft.setValue(LogicalValue.FALSE);
            firstBranch.addFront(newLeft);
            Sequence secondBranch = new Sequence(sigma);
            Formula newRight = right.clone();
            newRight.setValue(LogicalValue.TRUE);
            secondBranch.addFront(newRight);
            return new Sequence[]{firstBranch, secondBranch};
        } else {
            Sequence resultSequence = new Sequence(sigma);
            Formula newRight = right.clone();
            newRight.setValue(LogicalValue.FALSE);
            resultSequence.addFront(newRight);
            Formula newLeft = left.clone();
            newLeft.setValue(LogicalValue.TRUE);
            resultSequence.addFront(newLeft);
            return new Sequence[]{resultSequence};
        }
    }

    @Override
    public Implication clone() {
        return new Implication(left.clone(), right.clone(), value);
    }

    @Override
    public String toString() {
        return StringUtils.formatFormula(left) + " -> " +
                StringUtils.formatFormula(right);
    }
}
