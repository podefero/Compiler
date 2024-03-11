lexer grammar KxiLexer;

// Lexer fragments
fragment ALPHA  :   [A-Za-z] ;
fragment DIGIT  :   [0-9] ;
fragment UNDER_SCORE    :   '_' ;
fragment LINE_ENDING    :  '\r\n' | '\n' | '\r' ;
fragment UNESCAPED_CHAR : [\u0020-\u0022\u0027-\u005B\u005D-\u005E\u0060-\u007E] ;
fragment ESCAPED_CHAR : '\\' [rtn] ;
fragment CHAR   :   UNESCAPED_CHAR | ESCAPED_CHAR ;
//Bool
fragment B  :   'b' ;
fragment O  :   'o' ;
fragment L  :   'l' ;
//True
fragment T  :   't' ;
fragment R  :   'r' ;
fragment U  :   'u' ;
fragment E  :   'e' ;
//False
fragment F  :   'f' ;
fragment A  :   'a' ;
fragment S  :   's' ;
//Break
fragment K  :   'k' ;
//case
fragment C  :   'c' ;
//class
//char
fragment H  :   'h' ;
//cin
fragment I  :   'i' ;
fragment N  :   'n' ;
//cout
//default
fragment D  :   'd' ;
//else
//for
//if
//int
//new
fragment W  :   'w' ;
//null
//public
fragment P  : 'p' ;
//private
fragment V  :   'v' ;
//return
//static
//string
fragment G  :   'g' ;
//switch
//this
//true
//void
//while
fragment M : 'm' ;




// Lexer Rules
//Keywords
BOOL    :   B O O L ;
BREAK   :   B R E A K ;
CASE    :   C A S E ;
CLASS   :   C L A S S ;
CHAR_KEY :   C H A R ;
CIN     :   C I N ;
COUT    :   C O U T ;
DEFAULT :   D E F A U L T ;
ELSE    :   E L S E ;
FALSE   :   F A L S E ;
FOR     :   F O R ;
IF      :   I F ;
INT     :   I N T ;
NEW     :   N E W ;
NULL    :   N U L L ;
PUBLIC  :   P U B L I C ;
PRIVATE :   P R I V A T E ;
RETURN  :   R E T U R N ;
STATIC  :   S T A T I C ;
STRING  :   S T R I N G ;
SWITCH  :   S W I T C H ;
THIS    :   T H I S ;
TRUE    :   T R U E ;
VOID    :   V O I D ;
WHILE   :   W H I L E ;

//Punctuation
SEMICOLON   :   ';' ;
LCURLY  :   '{' ;
RCURLY  :   '}' ;
LPARENTH    :   '(' ;
RPARENTH    :   ')' ;
LBRACKET    :   '[' ;
RBRACKET    :   ']' ;
EQUALS  :   '=' ;
EQUALSEQUALS    :   '=''=' ;
NOTEQUALS   :   '!''=' ;
GREATEREQUALS   :   '>''=' ;
LESSEQUALS  :   '<''=' ;
LESSTHEN    :   '<' ;
GREATERTHEN :   '>' ;
AND  :   '&''&' ;
OR  :   '|''|' ;
NOT :   '!' ;
PLUS    :   '+' ;
SUBTRACT    :   '-' ;
MULT    :   '*' ;
DIVIDE  :   '/' ;
PLUSEQUALS   :   '+''=' ;
SUBEQUALS   :   '-''=' ;
MULTEQUALS  :   '*''=' ;
DIVEQUALS   :   '/''=' ;
OUTSTREAM    :   '<''<' ;
INSTREAM    :   '>''>' ;
DOT :   '.' ;
COMMA   :   ',' ;
COLON   : ':' ;

IDENTIFIER :    (ALPHA | UNDER_SCORE) (ALPHA | DIGIT | UNDER_SCORE)* ;
CHARLIT : '\'' (UNESCAPED_CHAR | ESCAPED_CHAR | '\\' | '\'' | '\\' | '"') '\'' ;
STRINGLIT : '"' (UNESCAPED_CHAR | ESCAPED_CHAR | '\\' | '\'' | '\\' | '"')* '"' ;
INTLIT     :    DIGIT+ ;
COMMENT : '//' ~[\r\n]* LINE_ENDING -> skip;
MULTI_COMMENT    : '/*' .* '*/' -> skip ;

// Whitespace and newlines
WS        : [ \t]+ -> skip ;
NEWLINE   : LINE_ENDING -> skip ;

//Unknown
UNKNOWN :   . ;