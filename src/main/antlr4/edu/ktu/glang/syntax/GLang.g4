grammar GLang;

program : statement* EOF ;

statement
    : printStmt ';'
    ;

printStmt : PRINT '(' INT ')' ;

// ---- Lexer ----
PRINT  : 'print' ;
INT    : [0-9]+ ; // NOTE - negative numbers not supported

COMMENT: ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip ;
WS     : [ \t\r\n]+ -> skip ;

// NOTE - you may not skip comments/whitespace, or use channel(HIDDEN)
// to get hidden tokens, use:
// List<Token> hidden = tokens.getHiddenTokensToLeft(index);
