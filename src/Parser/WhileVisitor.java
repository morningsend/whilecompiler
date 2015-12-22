// Generated from D:/java/WhileCompiler/antlr\While.g4 by ANTLR 4.5.1
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Parser.WhileParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WhileVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(WhileParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(WhileParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#readStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadStatement(WhileParser.ReadStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(WhileParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStatement(WhileParser.WriteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#writeLineStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteLineStatement(WhileParser.WriteLineStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#skipStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkipStatement(WhileParser.SkipStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(WhileParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(WhileParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(WhileParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(WhileParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(WhileParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(WhileParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(WhileParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(WhileParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#arrayDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDefinition(WhileParser.ArrayDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#parenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpression(WhileParser.ParenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#booleanConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanConstant(WhileParser.BooleanConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link Parser.WhileParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(WhileParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithMulDivExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithMulDivExpression(WhileParser.ArithMulDivExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpression(WhileParser.AssignExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RelationExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationExpression(WhileParser.RelationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Operand}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(WhileParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanAndExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanAndExpression(WhileParser.BooleanAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithUnaryExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithUnaryExpression(WhileParser.ArithUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithAddSubExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithAddSubExpression(WhileParser.ArithAddSubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanOrExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanOrExpression(WhileParser.BooleanOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanUnaryExpression}
	 * labeled alternative in {@link Parser.WhileParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanUnaryExpression(WhileParser.BooleanUnaryExpressionContext ctx);
}