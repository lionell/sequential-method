package io.github.lionell.utils;

import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.operations.binary.Implication;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.formulas.Predicate;
import io.github.lionell.formulas.quantifiers.Exists;

import java.util.ArrayList;
import java.util.List;

public class FormulaParser {
    private String input;
    private Formula formula;

    public FormulaParser(String input) {
        this.input = input;
    }

    public void run() {
        if (input.equals("true")) {
            formula = getDeducible();
        } else if (input.equals("false")) {
            formula = getNotDeducible();
        }
    }

    public Formula getFormula() {
        return formula;
    }

    private Formula getDeducible() {
        System.out.println("#xP[x] -> Q[x] |= P[x] -> #xQ[x]");
        List<String> arguments = new ArrayList<>();
        arguments.add("x");
        Exists ex1 = new Exists("x", new Predicate("P", arguments));
        Implication im1 = new Implication(ex1, new Predicate("Q", arguments));
        Exists ex2 = new Exists("x", new Predicate("Q", arguments));
        Implication im2 = new Implication(new Predicate("P", arguments), ex2);
        Implication im3 = new Implication(im1, im2);
        im3.setValue(LogicalValue.FALSE);
        return im3;
    }

    private Formula getNotDeducible() {
        System.out.println("P[x] -> #xQ[x] |= #xP[x] -> Q[x]");
        List<String> arguments = new ArrayList<>();
        arguments.add("x");
        Exists ex1 = new Exists("x", new Predicate("Q", arguments));
        Implication im1 = new Implication(new Predicate("P", arguments), ex1);
        Exists ex2 = new Exists("x", new Predicate("P", arguments));
        Implication im2 = new Implication(ex2, new Predicate("Q", arguments));
        Implication im3 = new Implication(im1, im2);
        im3.setValue(LogicalValue.FALSE);
        return im3;
    }
}
