grammar GLang;

program : statement* EOF ;

statement
    : letDecl ';'
    | assignment ';'
    | printStmt ';'
    ;

letDecl    : LET ID ( '=' expr )? ;
assignment : ID '=' expr ;
printStmt  : PRINT '(' expr ')' ;

expr
    : INT
    | ID
    ;

// ---- Lexer ----
LET    : 'let' ;
PRINT  : 'print' ;

ID     : [a-zA-Z_][a-zA-Z_0-9]* ; //NOTE - must be after keywords
INT    : [0-9]+ ; // NOTE - negative numbers not supported

COMMENT: ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip ;
WS     : [ \t\r\n]+ -> skip ;

// NOTE - you may not skip comments/whitespace, or use channel(HIDDEN)
// to get hidden tokens, use:
// List<Token> hidden = tokens.getHiddenTokensToLeft(index);
