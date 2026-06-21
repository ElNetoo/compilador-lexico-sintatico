package compilador.parser;

import compilador.lexer.Token;
import compilador.lexer.TokenType;

public class Parser {
    private TokenBuffer buffer;

    public Parser(TokenBuffer buffer) {
        this.buffer = buffer;
    }

    // Ponto de entrada da análise
    public String parse() {
        String resultado = E();
        buffer.match(TokenType.EOF); // garante que consumiu tudo
        return resultado;
    }

    // E → T E'
    private String E() {
        String t = T();
        return Linha(t);
    }

    // E' → (+|-) T E' | ε
    private String Linha(String herdado) {
        TokenType atual = buffer.current().type();
        if (atual == TokenType.OP_SOMA || atual == TokenType.OP_SUB) {
            Token op = buffer.match(atual);
            String t = T();
            String simbolo = op.type() == TokenType.OP_SOMA ? "+" : "-";
            String novo = simbolo + " " + herdado + " " + t;
            return Linha(novo);
        }
        return herdado; // ε
    }

    // T → P T'
    private String T() {
        String p = P();
        return LinhaT(p);
    }

    // T' → (*|/|%) P T' | ε
    private String LinhaT(String herdado) {
        TokenType atual = buffer.current().type();
        if (atual == TokenType.OP_MUL || atual == TokenType.OP_DIV || atual == TokenType.OP_MOD) {
            Token op = buffer.match(atual);
            String p = P();
            String simbolo = switch (op.type()) {
                case OP_MUL -> "*";
                case OP_DIV -> "/";
                case OP_MOD -> "%";
                default -> throw new IllegalStateException();
            };
            String novo = simbolo + " " + herdado + " " + p;
            return LinhaT(novo);
        }
        return herdado; // ε
    }

    // P → U P'
    private String P() {
        String u = U();
        return LinhaP(u);
    }

    // P' → ^ U P' | ε   (associatividade à direita)
    private String LinhaP(String herdado) {
        if (buffer.current().type() == TokenType.OP_EXP) {
            buffer.match(TokenType.OP_EXP);
            String u = U();
            String direita = LinhaP(u);
            return "^ " + herdado + " " + direita;
        }
        return herdado; // ε
    }

    // U → +U | -U | F
    private String U() {
        TokenType atual = buffer.current().type();
        if (atual == TokenType.OP_SOMA) {
            buffer.match(TokenType.OP_SOMA);
            return "+ " + U();
        }
        if (atual == TokenType.OP_SUB) {
            buffer.match(TokenType.OP_SUB);
            return "- " + U();
        }
        return F();
    }

    // F → ( E ) | INT | REAL | ID
    private String F() {
        Token atual = buffer.current();
        switch (atual.type()) {
            case AP -> {
                buffer.match(TokenType.AP);
                String e = E();
                buffer.match(TokenType.FP);
                return e;
            }
            case INT -> {
                return buffer.match(TokenType.INT).lexema();
            }
            case REAL -> {
                return buffer.match(TokenType.REAL).lexema();
            }
            case ID -> {
                return buffer.match(TokenType.ID).lexema();
            }
            default -> throw new RuntimeException(
                String.format("Erro sintático: token inesperado %s ('%s')",
                    atual.type(), atual.lexema())
            );
        }
    }
}
