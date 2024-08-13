parser grammar KxiParser;

options { tokenVocab=KxiLexer; }

compilationUnit : classDefinition* VOID ID LPAREN RPAREN block ;
classDefinition : CLASS ID LCURLY classMemberDefinition* RCURLY ;
scalarType : VOID | INT | CHAR | BOOL | STRING | ID ;
type : scalarType (LSQUARE RSQUARE)* ;
modifier : PUBLIC | PRIVATE ;
classMemberDefinition : methodDeclaration | dataMemberDeclaration | constructorDeclaration ;
dataMemberDeclaration : STATIC? modifier variableDeclaration ;
methodDeclaration : STATIC? modifier type methodSuffix ;
constructorDeclaration : methodSuffix ;
methodSuffix : ID LPAREN parameterList? RPAREN block ;
parameterList : parameter (COMMA parameter)* ;
parameter : type ID ;
variableDeclaration : type ID initializer? SEMICOLON ;
initializer : EQ expression ;
statement : IF LPAREN expression RPAREN statement (ELSE statement)?
            | WHILE LPAREN expression RPAREN statement
            | FOR LPAREN expression? SEMICOLON expression SEMICOLON expression? RPAREN statement
            | RETURN expression? SEMICOLON
            | COUT EXTRACT expression SEMICOLON
            | CIN INSERT expression SEMICOLON
            | SWITCH LPAREN expression RPAREN caseBlock
            | BREAK SEMICOLON
            | expression SEMICOLON
            | block
            | variableDeclaration ;
block : LCURLY statement* RCURLY ;
caseBlock : LCURLY case* DEFAULT COLON statement* RCURLY ;
case : CASE (INTLIT | CHARLIT) COLON statement* ;
expression :
            //l to r
            LPAREN expression RPAREN
            | expression index
            | expression arguments
            | expression DOT ID
            | NEW ID arguments
            | NEW type index
            //r to l
            | NOT expression
            | PLUS expression
            | MINUS expression
            //r to l
            //l to r

            | expression (TIMES | DIVIDE) expression
            | expression (PLUS | MINUS) expression
            | expression (LT | GT | LEQ | GEQ)  expression
            | expression (EQEQ | NOTEQ) expression
            | expression AND expression
            | expression OR expression
            // r to l
            |  <assoc=right> expression (EQ | PLUSEQ | MINUSEQ | TIMESEQ | DIVIDEEQ) expression
            | INTLIT
            | CHARLIT
            | STRINGLIT
            | TRUE
            | FALSE
            | NULL
            | THIS
            | ID ;
arguments : LPAREN argumentList? RPAREN ;
argumentList : expression (COMMA expression)* ;
index : LSQUARE expression RSQUARE ;