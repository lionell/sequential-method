package io.github.lionell;

import io.github.lionell.utils.analysis.lexer.Tokenizer;
import io.github.lionell.utils.analysis.tokens.Token;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class App {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("  ()[],  Predicate name && || -> !! #!@ -->");
        tokenizer.run();
        for (Token token : tokenizer.getTokens()) {
            System.out.println(token.getType() + " --> " + token.getValue());
        }
    }
}
