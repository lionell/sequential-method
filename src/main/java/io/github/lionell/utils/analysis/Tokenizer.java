package io.github.lionell.utils.analysis;

import io.github.lionell.exceptions.TokenizerException;
import io.github.lionell.utils.analysis.tokens.Token;
import io.github.lionell.utils.analysis.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Tokenizer {
    private List<Token> tokens = new ArrayList<>();
    private String input;
    private int index = 0;

    public Tokenizer(String input) {
        this.input = input;
    }

    public void run() {
        skipWhitespaces();
        while (index < input.length()) {
            Token token = new Token();
            char c = input.charAt(index);
            switch (c) {
                case '(':
                    token.setType(TokenType.OPEN_ROUND_BRACKET);
                    token.setValue("(");
                    break;
                case ')':
                    token.setType(TokenType.CLOSED_ROUND_BRACKET);
                    token.setValue(")");
                    break;
                case '[':
                    token.setType(TokenType.OPEN_BRACKET);
                    token.setValue("[");
                    break;
                case ']':
                    token.setType(TokenType.CLOSED_BRACKET);
                    token.setValue("]");
                    break;
                case ',':
                    token.setType(TokenType.COMMA);
                    token.setValue(",");
                    break;
                case '&':
                    token.setType(TokenType.AND);
                    token.setValue("&&");
                    break;
                case '|':
                    token.setType(TokenType.OR);
                    token.setValue("||");
                    break;
                case '=':
                    token.setType(TokenType.ASSUME);
                    token.setValue("=");
                    break;
                case '-':
                    token.setType(TokenType.IMPLIES);
                    token.setValue("->");
                    break;
                case '!':
                    token.setType(TokenType.DENY);
                    token.setValue("!");
                    break;
                case '#':
                    token.setType(TokenType.EXISTS);
                    token.setValue("#");
                    break;
                case '@':
                    token.setType(TokenType.FOR_ALL);
                    token.setValue("@");
                    break;
                default:
                    if (Character.isAlphabetic(c)) {
                        if (Character.isUpperCase(c)) {
                            token.setType(TokenType.PREDICATE);
                            token.setValue(getName());
                        } else {
                            token.setType(TokenType.VARIABLE);
                            token.setValue(getName());
                        }
                    } else {
                        throw new TokenizerException("Unknown symbol!");
                    }
            }
            match(token);
            tokens.add(token);
        }
    }

    private void skipWhitespaces() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
    }

    private String getName() {
        int end = index;
        while (end < input.length() && Character.isAlphabetic(input.charAt(end))) {
            end++;
        }
        return input.substring(index, end);
    }

    private void match(Token token) {
        for (char c : token.getValue().toCharArray()) {
            if (input.charAt(index) != c) {
                throw new TokenizerException("Expected '" + c
                        + "' found '" + input.charAt(index)
                        + "' at position " + index);
            }
            index++;
        }
        skipWhitespaces();
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
