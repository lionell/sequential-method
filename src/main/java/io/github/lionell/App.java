package io.github.lionell;

import io.github.lionell.utils.analysis.InfixToPostfixConverter;
import io.github.lionell.utils.analysis.Tokenizer;
import io.github.lionell.utils.analysis.tokens.Lexeme;
import io.github.lionell.utils.analysis.tokens.Token;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class App {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer("(#x(P[x]) -> Q[x]) = @xP[x] -> Q[x]");
        tokenizer.run();
        for (Lexeme lexeme : tokenizer.getLexemes()) {
            System.out.println(lexeme.getType() + " --> " + lexeme.getValue());
        }
        InfixToPostfixConverter converter = new InfixToPostfixConverter(tokenizer.getLexemes());
        converter.run();
        for (Token token : converter.getRPN()) {
            System.out.println(token.getType());
        }
    }
}
