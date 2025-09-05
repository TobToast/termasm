// Generated from Assembly.g4 by ANTLR 4.13.2

package tobi.tools.hardware;


import java.nio.charset.StandardCharsets;
import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AssemblyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AssemblyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#assemblyprog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssemblyprog(AssemblyParser.AssemblyprogContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(AssemblyParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#labeldef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeldef(AssemblyParser.LabeldefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(AssemblyParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(AssemblyParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#indirectOperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndirectOperand(AssemblyParser.IndirectOperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#directOperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDirectOperand(AssemblyParser.DirectOperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#ioOperand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIoOperand(AssemblyParser.IoOperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(AssemblyParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#constantExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpression(AssemblyParser.ConstantExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblyParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(AssemblyParser.NumberContext ctx);
}