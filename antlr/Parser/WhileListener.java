// Generated from D:/java/WhileCompiler/antlr\While.g4 by ANTLR 4.5.1
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WhileParser}.
 */
public interface WhileListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WhileParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(WhileParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(WhileParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(WhileParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(WhileParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadStatement(WhileParser.ReadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadStatement(WhileParser.ReadStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(WhileParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(WhileParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#writeStatement}.
	 * @param ctx the parse tree
	 */
	void enterWriteStatement(WhileParser.WriteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#writeStatement}.
	 * @param ctx the parse tree
	 */
	void exitWriteStatement(WhileParser.WriteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#writeLineStatement}.
	 * @param ctx the parse tree
	 */
	void enterWriteLineStatement(WhileParser.WriteLineStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#writeLineStatement}.
	 * @param ctx the parse tree
	 */
	void exitWriteLineStatement(WhileParser.WriteLineStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#skipStatement}.
	 * @param ctx the parse tree
	 */
	void enterSkipStatement(WhileParser.SkipStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#skipStatement}.
	 * @param ctx the parse tree
	 */
	void exitSkipStatement(WhileParser.SkipStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(WhileParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(WhileParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(WhileParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(WhileParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(WhileParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(WhileParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(WhileParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(WhileParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(WhileParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(WhileParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(WhileParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(WhileParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(WhileParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(WhileParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(WhileParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(WhileParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#arrayDefinition}.
	 * @param ctx the parse tree
	 */
	void enterArrayDefinition(WhileParser.ArrayDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#arrayDefinition}.
	 * @param ctx the parse tree
	 */
	void exitArrayDefinition(WhileParser.ArrayDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#parenExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(WhileParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#parenExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(WhileParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#booleanConstant}.
	 * @param ctx the parse tree
	 */
	void enterBooleanConstant(WhileParser.BooleanConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#booleanConstant}.
	 * @param ctx the parse tree
	 */
	void exitBooleanConstant(WhileParser.BooleanConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link WhileParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(WhileParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WhileParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(WhileParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArithMulDivExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArithMulDivExpression(WhileParser.ArithMulDivExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArithMulDivExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArithMulDivExpression(WhileParser.ArithMulDivExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpression(WhileParser.AssignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpression(WhileParser.AssignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RelationExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationExpression(WhileParser.RelationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RelationExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationExpression(WhileParser.RelationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Operand}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOperand(WhileParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Operand}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOperand(WhileParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanAndExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAndExpression(WhileParser.BooleanAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanAndExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAndExpression(WhileParser.BooleanAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArithUnaryExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArithUnaryExpression(WhileParser.ArithUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArithUnaryExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArithUnaryExpression(WhileParser.ArithUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArithAddSubExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArithAddSubExpression(WhileParser.ArithAddSubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArithAddSubExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArithAddSubExpression(WhileParser.ArithAddSubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanOrExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanOrExpression(WhileParser.BooleanOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanOrExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanOrExpression(WhileParser.BooleanOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanUnaryExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanUnaryExpression(WhileParser.BooleanUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanUnaryExpression}
	 * labeled alternative in {@link WhileParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanUnaryExpression(WhileParser.BooleanUnaryExpressionContext ctx);
}