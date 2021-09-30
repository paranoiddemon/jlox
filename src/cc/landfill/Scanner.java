package cc.landfill;

import java.util.ArrayList;
import java.util.List;

import static cc.landfill.TokenType.*;

// from source to tokens
public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    /*
    mark the position of source code.
    start and current: marks the character, start pos and current pos
    line is current line
    */
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        // END OFF FILE
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        // move forward of the source string
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
            case ')':
                addToken(RIGHT_PAREN);
            case '{':
                addToken(LEFT_BRACE);
            case '}':
                addToken(RIGHT_BRACE);
            case ',':
                addToken(COMMA);
            case '.':
                addToken(DOT);
            case '-':
                addToken(MINUS);
            case '+':
                addToken(PLUS);
            case ';':
                addToken(SEMICOLON);
            case '*':
                addToken(STAR);

                //two stage lexemes
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '<':

                //longer lexeme
            case '/':
                if (match('/')) {
                    // skip the comment line, use peek instead of match to update line
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;

            // ignore white space: space, return, tab
            case ' ':
            case '\r':
            case '\t':
                break;

            case '\n':
                line++;
                break;


            // string literal
            case '"':
                string();
                break;

            // erroneous char is consumed, keep scanning
            default:
                Lox.error(line, "Unexpected character.");
                break;
        }
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if(peek() == '\n') line++;
            advance();
        }
    }


    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        // called advance before match, so the current point to the next position;
        // if match, move forward
        current++;
        return true;

    }

    private char advance() {
        return source.charAt(current++);//self increment after charAt
    }

    private void addToken(TokenType type) {

        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

}