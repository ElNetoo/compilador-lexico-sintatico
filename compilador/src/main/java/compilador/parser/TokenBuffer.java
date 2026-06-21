package compilador.parser;

import compilador.lexer.Lexer;
import compilador.lexer.Token;
import compilador.lexer.TokenType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenBuffer {
    private Lexer lexer;
    private List<Token> tokens;
    private int pos;

    public TokenBuffer(Lexer lexer) throws IOException {
        this.lexer = lexer;
        this.tokens = new ArrayList<>();
        this.pos = 0;

        Token token;
        do {
            token = lexer.readNextToken();
            tokens.add(token);
        } while (token.type() != TokenType.EOF);
    }

    public Token lookahead(int k) {
        int index = pos + k;
        if (index >= tokens.size()) {
            return tokens.get(tokens.size() - 1);
        }
        return tokens.get(index);
    }

    public Token current() {
        return lookahead(0);
    }

    public Token match(TokenType expected) {
        Token atual = current();
        if (atual.type() != expected) {
            throw new RuntimeException(
                String.format("Erro sintático: esperado %s mas encontrado %s ('%s')",
                    expected, atual.type(), atual.lexema())
            );
        }
        pos++;
        return atual;
    }
}