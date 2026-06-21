package compilador;

import compilador.lexer.Lexer;
import compilador.parser.Parser;
import compilador.parser.TokenBuffer;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: mvn exec:java -Dexec.args=\"teste.txt\"");
            return;
        }

        String fileName = args[0];

        try {
            Lexer lexer = new Lexer(fileName);
            TokenBuffer buffer = new TokenBuffer(lexer);
            Parser parser = new Parser(buffer);
            String resultado = parser.parse();
            System.out.println("Notação Prefixa: " + resultado);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}