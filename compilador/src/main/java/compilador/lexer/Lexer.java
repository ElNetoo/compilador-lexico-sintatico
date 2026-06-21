package compilador.lexer;

import java.io.Closeable;
import java.io.IOException;
import java.util.regex.Pattern;

public class Lexer implements Closeable {
    private Buffer buffer;
    private static final Pattern WHITESPACE = Pattern.compile("[ \\t]+");

    public Lexer(String fileName) throws IOException {
        this.buffer = new Buffer(fileName);
    }

    public Token readNextToken() throws IOException {
        while (!buffer.isEOF()) {
            if (buffer.isEOL()) {
                buffer.readNextLine();
            } else {
                // Ignora espaços e tabs
                var spaces = buffer.tryMatch(WHITESPACE);
                if (spaces != null) {
                    buffer.consume(spaces);
                    continue;
                }

                for (var tp : TokenPattern.values()) {
                    var lexema = buffer.tryMatch(tp.getPattern());
                    if (lexema != null) {
                        buffer.consume(lexema);
                        return new Token(lexema, tp.getType());
                    }
                }

                throw new RuntimeException(
                    String.format("Erro léxico: caractere não reconhecido '%s' na linha %d, coluna %d",
                        buffer.getCurrentChar(), buffer.getRow(), buffer.getCol() + 1)
                );
            }
        }
        return new Token("", TokenType.EOF);
    }

    @Override
    public void close() throws IOException {
        if (buffer != null) buffer.close();
    }
}