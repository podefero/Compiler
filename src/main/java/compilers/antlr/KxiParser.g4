parser grammar KxiParser;

options { tokenVocab=KxiLexer; }

compilationUnit : classDefinition* VOID MAIN LPARENTH RPARENTH block ;
classDefinition : CLASS IDENTIFIER LCURLY classMemberDefinition* RCURLY ;
scalarType : VOID | INT | CHAR | BOOL | STRING | IDENTIFIER ;
type : scalarType (LBRACKET RBRACKET)* ;
modifier : PUBLIC | PRIVATE ;
classMemberDefinition : methodDeclaration | dataMemberDeclaration | constructorDeclaration ;
dataMemberDeclaration : STATIC? modifier variableDeclaration ;
methodDeclaration : STATIC? modifier type methodSuffix ;
constructorDeclaration : methodSuffix ;
methodSuffix : IDENTIFIER LPARENTH parameterList RPARENTH block ;
parameterList : parameter (COMMA parameter)* ;
parameter : type IDENTIFIER ;
variableDeclaration : type IDENTIFIER initializer? SEMICOLON ;
initializer : EQUALS expression ;
statement : IF LPARENTH expression RPARENTH statement (ELSE statement)?
            | WHILE LPARENTH expression RPARENTH statement
            | FOR LPARENTH expression? SEMICOLON expression SEMICOLON expression? RPARENTH statement
            | RETURN expression? SEMICOLON
            | COUT OUTSTREAM expression SEMICOLON
            | CIN INSTREAM expression SEMICOLON
            | SWITCH LPARENTH expression RPARENTH caseBlock
            | BREAK SEMICOLON
            | block
            | variableDeclaration ;
block : LCURLY statement* RCURLY ;
caseBlock : LCURLY case* DEFAULT COLON statement* RCURLY ;
case : CASE (INTLIT | CHARLIT) COLON statement* ;
expression : expression EQUALS expression
            | expression PLUSEQUALS expression
            | expression SUBEQUALS expression
            | expression MULTEQUALS expression
            | expression DIVEQUALS expression
            | expression PLUS expression
            | expression SUBTRACT expression
            | expression MULT expression
            | expression DIVIDE expression
            | expression EQUALSEQUALS expression
            | expression NOTEQUALS expression
            | expression LESSTHEN expression
            | expression GREATERTHEN expression
            | expression LESSEQUALS expression
            | expression GREATEREQUALS expression
            | expression AND expression
            | expression OR expression
            | NOT expression
            | PLUS expression
            | SUBTRACT expression
            | LPARENTH expression RPARENTH
            | INTLIT
            | CHARLIT
            | STRINGLIT
            | TRUE
            | FALSE
            | NULL
            | THIS
            | IDENTIFIER
            | NEW type (arguments | index)
            | expression DOT IDENTIFIER
            | expression index
            | expression arguments ;
arguments : LPARENTH argumentList? RPARENTH ;
argumentList : expression (COMMA expression)* ;
index : LBRACKET expression RBRACKET ;
