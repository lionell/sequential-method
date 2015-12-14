package io.github.lionell.utils.analysis;

import io.github.lionell.formulas.Formula;
import io.github.lionell.miscellaneous.LogicalValue;

public class Parser {
    private String input;
    private Formula formula;

    public Parser(String input) {
        this.input = input;
    }

    public void run() {
        Tokenizer tokenizer = new Tokenizer(input);
        tokenizer.run();
        InfixToPostfixConverter converter =
                new InfixToPostfixConverter(tokenizer.getLexemes());
        converter.run();
        FormulaGenerator generator = new FormulaGenerator(converter.getRPN());
        generator.run();
        formula = generator.getFormula();
        formula.setValue(LogicalValue.FALSE);
    }

    public Formula getFormula() {
        return formula;
    }

//    private Formula getDeducible() {
//        System.out.println("#xP[x] -> Q[x] = P[x] -> #xQ[x]");
//        List<String> arguments = new ArrayList<>();
//        arguments.add("x");
//        Exists ex1 = new Exists("x", new Predicate("P", arguments));
//        Implication im1 = new Implication(ex1, new Predicate("Q", arguments));
//        Exists ex2 = new Exists("x", new Predicate("Q", arguments));
//        Implication im2 = new Implication(new Predicate("P", arguments), ex2);
//        Implication im3 = new Implication(im1, im2);
//        im3.setValue(LogicalValue.FALSE);
//        return im3;
//    }
//
//    private Formula getNotDeducible() {
//        System.out.println("P[x] -> #xQ[x] = #xP[x] -> Q[x]");
//        List<String> arguments = new ArrayList<>();
//        arguments.add("x");
//        Exists ex1 = new Exists("x", new Predicate("Q", arguments));
//        Implication im1 = new Implication(new Predicate("P", arguments), ex1);
//        Exists ex2 = new Exists("x", new Predicate("P", arguments));
//        Implication im2 = new Implication(ex2, new Predicate("Q", arguments));
//        Implication im3 = new Implication(im1, im2);
//        im3.setValue(LogicalValue.FALSE);
//        return im3;
//    }
}