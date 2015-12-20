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
public class Disjunction extends BinaryOperation {
    public Disjunction(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public Disjunction(Formula left, Formula right, LogicalValue value) {
        this(left, right);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        if (!isEvaluated()) {
            throw new LogicalException("Can't expand not evaluated disjunction!");
        }
        if (value == LogicalValue.TRUE) {
            Sequence firstBranch = new Sequence(sigma);
            Formula newLeft = left.clone();
            newLeft.setValue(LogicalValue.TRUE);
            firstBranch.addFront(newLeft);
            Sequence secondBranch = new Sequence(sigma);
            Formula newRight = right.clone();
            newRight.setValue(LogicalValue.TRUE);
            secondBranch.addFront(newRight);
            return new Sequence[]{firstBranch, secondBranch};
        } else {
            Sequence resultSequence = new Sequence(sigma);
            Formula newLeft = left.clone();
            newLeft.setValue(LogicalValue.FALSE);
            resultSequence.addFront(newLeft);
            Formula newRight = right.clone();
            newRight.setValue(LogicalValue.FALSE);
            resultSequence.addFront(newRight);
            return new Sequence[]{resultSequence};
        }
    }

    @Override
    public Disjunction clone() {
        return new Disjunction(left.clone(), right.clone(), value);
    }

    @Override
    public String toString() {
        return StringUtils.formatFormula(left) + " | " +
                StringUtils.formatFormula(right);
    }
}
