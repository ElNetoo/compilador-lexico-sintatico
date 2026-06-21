# 🔍 Compilador — Analisador Léxico e Sintático

Implementação de um analisador léxico e sintático preditivo por descendência recursiva com Tradução Dirigida pela Sintaxe (TDS), capaz de converter expressões aritméticas da notação infixa para a notação prefixa.

> Projeto desenvolvido para a disciplina de Compiladores.

---

## 🎯 Objetivo

Receber expressões aritméticas escritas na **notação infixa** e convertê-las automaticamente para a **notação prefixa**, respeitando precedência e associatividade dos operadores.

---

## Exemplos

| Entrada (Infixa)      | Saída (Prefixa)   |
|-----------------------|-------------------|
| `A + B * C`           | `+ A * B C`       |
| `(A + B) / (C + D)`  | `/ + A B + C D`   |
| `A * B + C * D`       | `+ * A B * C D`   |
| `2 + -5 * 3`          | `+ 2 * - 5 3`     |
| `5 ^ -2`              | `^ 5 - 2`         |
| `-(A + B)`            | `- + A B`         |

---

## Exemplos de Erro

| Entrada            | Erro Reportado                                          |
|--------------------|---------------------------------------------------------|
| `A + @ B`          | `Erro léxico: caractere não reconhecido '@'`            |
| `A + * B`          | `Erro sintático: token inesperado OP_MUL ('*')`         |
| `(A + B`           | `Erro sintático: esperado FP mas encontrado EOF ('')`   |

---

## Operadores Suportados

| Operador       | Símbolo | Precedência   |
|----------------|---------|---------------|
| Soma           | `+`     | 1 — menor     |
| Subtração      | `-`     | 1             |
| Multiplicação  | `*`     | 2             |
| Divisão        | `/`     | 2             |
| Módulo         | `%`     | 2             |
| Exponenciação  | `^`     | 3             |
| Unário `+`/`-` | `+` `-` | 4 — maior     |

---

## Gramática LL(1)

```
E  → T E'
E' → (+ | -) T E' | ε
T  → P T'
T' → (* | / | %) P T' | ε
P  → U P'
P' → ^ U P' | ε
U  → +U | -U | F
F  → ( E ) | INT | REAL | ID
```

---

## 🗂️ Estrutura de pastas do projeto

```
src/
└── main/java/compilador/
    ├── Main.java                 # Ponto de entrada
    ├── lexer/
    │   ├── Token.java            # Representa um token (lexema + tipo)
    │   ├── TokenType.java        # Enum com os tipos de token
    │   ├── TokenPattern.java     # Padrões regex para reconhecimento
    │   ├── Buffer.java           # Leitura do arquivo caractere a caractere
    │   └── Lexer.java            # Analisador léxico
    └── parser/
        ├── TokenBuffer.java      # Buffer de tokens com lookahead(k)
        └── Parser.java           # Analisador sintático preditivo + TDS
```

---

## Como rodar o projeto

### Pré-requisitos
- Java 17+
- Maven

### Passo-a-passo

```bash

git clone https://github.com/ElNetoo/compilador-lexico-sintatico.git
cd compilador-lexico-sintatico/compilador

mvn compile

mvn exec:java "-Dexec.args=teste.txt"
```

### Arquivo de entrada

Crie um arquivo `testes.txt` na raiz do projeto com a expressão na notação infixa:

```
A + B * C
```

> ⚠️ Tem que salvar o arquivo com encoding **UTF-8 sem BOM** para funcionar, pois pode acontecer de dar erro com codificação.

---

## Tecnologias

- Java 17+
- Maven

---

## 👨‍💻 Autores

| Nome | GitHub |
|------|--------|
| Francisco Neto | [@ElNetoo](https://github.com/ElNetoo) |
| Ianny Kerlyn | [@iannyk](https://github.com/iannyk) |
