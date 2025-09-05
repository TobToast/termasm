// Generated from /media/tobias/Daten/VS-Code_Workspace/termasm/src/main/antlr/Assembly.g4 by ANTLR 4.13.1

package tobi.tools.hardware;


import java.nio.charset.StandardCharsets;
import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AssemblyParser}.
 */
public interface AssemblyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#assemblyprog}.
	 * @param ctx the parse tree
	 */
	void enterAssemblyprog(AssemblyParser.AssemblyprogContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#assemblyprog}.
	 * @param ctx the parse tree
	 */
	void exitAssemblyprog(AssemblyParser.AssemblyprogContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(AssemblyParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(AssemblyParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#labeldef}.
	 * @param ctx the parse tree
	 */
	void enterLabeldef(AssemblyParser.LabeldefContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#labeldef}.
	 * @param ctx the parse tree
	 */
	void exitLabeldef(AssemblyParser.LabeldefContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(AssemblyParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(AssemblyParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(AssemblyParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(AssemblyParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#indirectOperand}.
	 * @param ctx the parse tree
	 */
	void enterIndirectOperand(AssemblyParser.IndirectOperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#indirectOperand}.
	 * @param ctx the parse tree
	 */
	void exitIndirectOperand(AssemblyParser.IndirectOperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#directOperand}.
	 * @param ctx the parse tree
	 */
	void enterDirectOperand(AssemblyParser.DirectOperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#directOperand}.
	 * @param ctx the parse tree
	 */
	void exitDirectOperand(AssemblyParser.DirectOperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#ioOperand}.
	 * @param ctx the parse tree
	 */
	void enterIoOperand(AssemblyParser.IoOperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#ioOperand}.
	 * @param ctx the parse tree
	 */
	void exitIoOperand(AssemblyParser.IoOperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(AssemblyParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(AssemblyParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(AssemblyParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(AssemblyParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblyParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(AssemblyParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblyParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(AssemblyParser.NumberContext ctx);
}