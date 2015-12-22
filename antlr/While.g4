/* Author Zaiyang Li */
grammar While;
import WhileLexerRules;

program: statements;

statements: statement (Semicolon statement)*;

readStatement: Read parenExpression;

blockStatement: OpenParen statements CloseParen;

writeStatement: Write parenExpression;

writeLineStatement: WriteLn;

skipStatement: Skip;

returnStatement: Return expression?;

functionDefinition: Function Identifier parameterList blockStatement;

ifStatement: If expression Then statement Else statement;

whileStatement: While expression Do statement;

statement: blockStatement 
         | skipStatement 
         | ifStatement
         | whileStatement 
         | writeStatement 
         | writeLineStatement
         | readStatement
         | expression
         | functionDefinition
         | returnStatement;



parameterList: OpenParen (expression (Comma expression)*)? CloseParen;

functionCall: Identifier parameterList;

arrayAccess: Identifier OpenSquareBracket expression CloseSquareBracket;

arrayDefinition: Array OpenParen Integer CloseParen;

parenExpression: OpenParen expression CloseParen;

booleanConstant: True | False;

factor: Float
        | Integer
        | booleanConstant
        | arrayAccess
        | arrayDefinition
        | functionCall
        | Identifier
        | parenExpression
        | String
;

expression: factor                                                          #Operand
          | (Plus | Minus) expression                                       #ArithUnaryExpression
          | expression (Multiply | Divide ) expression                      #ArithMulDivExpression
          | expression (Plus | Minus) expression                            #ArithAddSubExpression
          | expression ( Equals | NotEquals | LessThan | GreaterThan | LessEqualThan | GreaterEqualThan) expression #RelationExpression
          | BooleanNot expression                                           #BooleanUnaryExpression
          | expression BooleanAnd expression                                #BooleanAndExpression
          | expression BooleanOr expression                                 #BooleanOrExpression
          | expression Assign expression                                    #AssignExpression
 ;


