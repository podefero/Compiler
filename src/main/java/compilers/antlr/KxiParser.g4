parser grammar KxiParser;

options { tokenVocab=KxiLexer; }

compilationUnit : classDefinition* VOID IDENTIFIER LPARENTH RPARENTH block ;
classDefinition : CLASS IDENTIFIER LCURLY classMemberDefinition* RCURLY ;
scalarType : VOID | INT | CHAR_KEY | BOOL | STRING | IDENTIFIER ;
type : scalarType (LBRACKET RBRACKET)* ;
modifier : PUBLIC | PRIVATE ;
classMemberDefinition : methodDeclaration | dataMemberDeclaration | constructorDeclaration ;
dataMemberDeclaration : STATIC? modifier variableDeclaration ;
methodDeclaration : STATIC? modifier type methodSuffix ;
constructorDeclaration : methodSuffix ;
methodSuffix : IDENTIFIER LPARENTH parameterList? RPARENTH block ;
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
            | expression SEMICOLON
            | block
            | variableDeclaration ;
block : LCURLY statement* RCURLY ;
caseBlock : LCURLY case* DEFAULT COLON statement* RCURLY ;
case : CASE (INTLIT | CHARLIT) COLON statement* ;
expression :
            LPARENTH expression RPARENTH
            | expression index
            | expression DOT IDENTIFIER
            | NOT expression
            | PLUS expression
            | SUBTRACT expression
            | NEW IDENTIFIER arguments
            | NEW type index
            | expression arguments
            | expression MULT expression
            | expression DIVIDE expression
            | expression PLUS expression
            | expression SUBTRACT expression
            | expression LESSTHEN expression
            | expression LESSEQUALS expression
            | expression GREATERTHEN expression
            | expression GREATEREQUALS expression
            | expression EQUALSEQUALS expression
            | expression NOTEQUALS expression
            | expression AND expression
            | expression OR expression
            | expression EQUALS expression
            | expression PLUSEQUALS expression
            | expression SUBEQUALS expression
            | expression MULTEQUALS expression
            | expression DIVEQUALS expression
            | INTLIT
            | CHARLIT
            | STRINGLIT
            | TRUE
            | FALSE
            | NULL
            | THIS
            | IDENTIFIER ;
arguments : LPARENTH argumentList? RPARENTH ;
argumentList : expression (COMMA expression)* ;
index : LBRACKET expression RBRACKET ;