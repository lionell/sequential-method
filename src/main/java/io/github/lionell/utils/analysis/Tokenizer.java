package io.github.lionell.utils.analysis;

import io.github.lionell.exceptions.TokenizerException;
import io.github.lionell.utils.analysis.tokens.Lexeme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Tokenizer {
    private List<Lexeme> lexemes = new ArrayList<>();
    private String input;
    private int index = 0;

    public Tokenizer(String input) {
        this.input = input;
    }

    public void run() {
        skipWhitespaces();
        while (index < input.length()) {
            Lexeme lexeme = new Lexeme();
            switch (getChar()) {
                case '(':
                    lexeme.setType(Lexeme.Type.OPEN_ROUND_BRACKET);
                    lexeme.setValue("(");
                    break;
                case ')':
                    lexeme.setType(Lexeme.Type.CLOSED_ROUND_BRACKET);
                    lexeme.setValue(")");
                    break;
                case '[':
                    lexeme.setType(Lexeme.Type.OPEN_BRACKET);
                    lexeme.setValue("[");
                    break;
                case ']':
                    lexeme.setType(Lexeme.Type.CLOSED_BRACKET);
                    lexeme.setValue("]");
                    break;
                case ',':
                    lexeme.setType(Lexeme.Type.COMMA);
                    lexeme.setValue(",");
                    break;
                case '&':
                    lexeme.setType(Lexeme.Type.AND);
                    lexeme.setValue("&");
                    break;
                case '|':
                    lexeme.setType(Lexeme.Type.OR);
                    lexeme.setValue("|");
                    break;
                case '=':
                    lexeme.setType(Lexeme.Type.ASSUME);
                    lexeme.setValue("=");
                    break;
                case '-':
                    lexeme.setType(Lexeme.Type.IMPLIES);
                    lexeme.setValue("->");
                    break;
                case '!':
                    lexeme.setType(Lexeme.Type.DENY);
                    lexeme.setValue("!");
                    break;
                case '#':
                    lexeme.setType(Lexeme.Type.EXISTS);
                    lexeme.setValue("#");
                    break;
                case '@':
                    lexeme.setType(Lexeme.Type.FOR_ALL);
                    lexeme.setValue("@");
                    break;
                default:
                    if (Character.isAlphabetic(getChar())) {
                        if (Character.isUpperCase(getChar())) {
                            lexeme.setType(Lexeme.Type.PREDICATE);
                            lexeme.setValue(getPredicateName());
                        } else {
                            lexeme.setType(Lexeme.Type.VARIABLE);
                            lexeme.setValue(getVariableName());
                        }
                    } else {
                        throw new TokenizerException("Unexpected symbol!");
                    }
            }
            match(lexeme);
            lexemes.add(lexeme);
        }
    }

    private void skipWhitespaces() {
        while (index < input.length() && Character.isWhitespace(getChar())) {
            index++;
        }
    }

    private String getLowerCaseName(int start) {
        int end = start;
        while (end < input.length() && Character.isLowerCase(input.charAt(end))) {
            end++;
        }
        return input.substring(start, end);
    }

    private String getPredicateName() {
        return Character.toString(getChar()) + getLowerCaseName(index + 1);
    }

    private String getVariableName() {
        return getLowerCaseName(index);
    }

    private char getChar() {
        return input.charAt(index);
    }

    private void match(Lexeme lexeme) {
        for (char c : lexeme.getValue().toCharArray()) {
            if (input.charAt(index) != c) {
                throw new TokenizerException("Expected '" + c
                        + "' found '" + input.charAt(index)
                        + "' at position " + index);
            }
            index++;
        }
        skipWhitespaces();
    }

    public List<Lexeme> getLexemes() {
        return lexemes;
    }
}
