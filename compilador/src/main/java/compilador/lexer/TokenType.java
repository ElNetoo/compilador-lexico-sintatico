package compilador.lexer;

public enum TokenType {
    
    INT,        
    REAL,       
    ID,         

    AP, // Abre parentese (
    FP, // Fecha parentese )

    OP_SOMA,    
    OP_SUB,     
    OP_MUL,     
    OP_DIV,     
    OP_MOD,     
    OP_EXP,     

    EOF
}