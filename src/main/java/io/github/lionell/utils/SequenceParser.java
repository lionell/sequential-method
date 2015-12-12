package io.github.lionell.utils;

import io.github.lionell.logic.LogicalValue;
import io.github.lionell.logic.Predicate;
import io.github.lionell.logic.Sequence;
import io.github.lionell.logic.operations.binary.Implication;
import io.github.lionell.logic.quantifiers.Exists;

import java.util.ArrayList;
import java.util.List;

public class SequenceParser {
    public SequenceParser() {
    }

    public Sequence parse(String example) {
        System.out.println(example);
        List<String> arguments = new ArrayList<>();
        arguments.add("x");
        Exists ex1 = new Exists("x", new Predicate("P", arguments));
        Implication im1 = new Implication(ex1, new Predicate("Q", arguments));
        Exists ex2 = new Exists("x", new Predicate("Q", arguments));
        Implication im2 = new Implication(new Predicate("P", arguments), ex2);
        Implication im3 = new Implication(im1, im2);
        im3.setValue(LogicalValue.FALSE);
        return new Sequence(im3);
    }
}
