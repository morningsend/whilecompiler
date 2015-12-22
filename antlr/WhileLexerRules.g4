/* Author Zaiyang Li */
lexer grammar WhileLexerRules;



/* Key words */
While: 'while';
Then: 'then';
Do: 'do';
If : 'if';
Else: 'else';
Write: 'write';
WriteLn: 'writeln';
Read: 'read';
Skip: 'skip';
True: 'true';
False: 'false';
Return: 'return';
Array: 'array';
Function: 'function';

fragment Digit: '0' .. '9';

Integer: Digit+;

Float: Digit '.' Digit*;

fragment Letters: 'A' .. 'Z'| 'a'.. 'z'; 

Identifier : Letters (Letters | Digit)*;

/* Symbols */
Assign: ':=';
Plus: '+';
Minus: '-';
Multiply: '*';
Divide	: '/';
Equals: '=';
NotEquals: '!=';
LessEqualThan: '<=';
LessThan: '<';
GreaterThan: '>';
GreaterEqualThan: '>=';
Comma: ',';
BooleanAnd: '&';
BooleanOr: '|';
BooleanNot: '!';
BooleanXor: '^';

Semicolon: ';';
OpenParen: '(';
CloseParen: ')';
OpenSquareBracket: '[';
CloseSquareBracket: ']';
/* special tokens*/
WhiteSpace: [ \t\r\n]+ -> skip;
String: '\'' ('\'' '\'' | ~'\'')* '\'';
Comment: '{' (~'}')* '}' -> skip;

