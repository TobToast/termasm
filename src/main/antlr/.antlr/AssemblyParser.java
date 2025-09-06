// Generated from /media/tobias/Daten/VS-Code_Workspace/termasm/src/main/antlr/Assembly.g4 by ANTLR 4.13.1

package tobi.tools.hardware;


import java.nio.charset.StandardCharsets;
import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class AssemblyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		HEX_NUMBER=18, BIN_NUMBER=19, DEC_NUMBER=20, ARROW=21, REGISTER=22, MNEMONIC=23, 
		ID=24, WS=25, COMMENT=26, MULTI_COMMENT=27;
	public static final int
		RULE_assemblyprog = 0, RULE_statement = 1, RULE_labeldef = 2, RULE_label = 3, 
		RULE_operand = 4, RULE_indirectOperand = 5, RULE_directOperand = 6, RULE_ioOperand = 7, 
		RULE_literal = 8, RULE_constantExpression = 9, RULE_number = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"assemblyprog", "statement", "labeldef", "label", "operand", "indirectOperand", 
			"directOperand", "ioOperand", "literal", "constantExpression", "number"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "':'", "'.'", "'#'", "'\\u00A7'", "'<'", "'>'", "'\"'", 
			"'''", "'('", "')'", "'*'", "'/'", "'+'", "'-'", "'^'", null, null, null, 
			"'->'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "HEX_NUMBER", "BIN_NUMBER", "DEC_NUMBER", 
			"ARROW", "REGISTER", "MNEMONIC", "ID", "WS", "COMMENT", "MULTI_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Assembly.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	  private static final java.util.Set<String> MNEMONICS = loadMnemonics();
	  private static java.util.Set<String> loadMnemonics() {
	    java.util.Set<String> set = new java.util.HashSet<>();
	    try (java.io.InputStream is = AssemblyLexer.class.getResourceAsStream("/ops.txt");
	         java.util.Scanner sc = new java.util.Scanner(is, java.nio.charset.StandardCharsets.UTF_8)) {
	      sc.nextLine(); // Header skip
	      while (sc.hasNextLine()) {
	        String token = sc.nextLine().split("\\s+")[0];
	        int l = token.indexOf('('), r = token.indexOf(')');
	        if (l>=0 && r>l) {
	          for (String m : token.substring(l+1,r).split("/")) {
	            set.add(m.toLowerCase());
	          }
	        }
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	    return set;
	  }

	public AssemblyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssemblyprogContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(AssemblyParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<LabeldefContext> labeldef() {
			return getRuleContexts(LabeldefContext.class);
		}
		public LabeldefContext labeldef(int i) {
			return getRuleContext(LabeldefContext.class,i);
		}
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public AssemblyprogContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assemblyprog; }
	}

	public final AssemblyprogContext assemblyprog() throws RecognitionException {
		AssemblyprogContext _localctx = new AssemblyprogContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assemblyprog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 27070096L) != 0)) {
				{
				setState(25);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(22);
					statement();
					}
					break;
				case 2:
					{
					setState(23);
					labeldef();
					}
					break;
				case 3:
					{
					setState(24);
					literal();
					}
					break;
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public OperandContext opa;
		public OperandContext opb;
		public NumberContext predicate;
		public TerminalNode MNEMONIC() { return getToken(AssemblyParser.MNEMONIC, 0); }
		public TerminalNode ARROW() { return getToken(AssemblyParser.ARROW, 0); }
		public TerminalNode REGISTER() { return getToken(AssemblyParser.REGISTER, 0); }
		public List<OperandContext> operand() {
			return getRuleContexts(OperandContext.class);
		}
		public OperandContext operand(int i) {
			return getRuleContext(OperandContext.class,i);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(MNEMONIC);
			setState(34);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(33);
				((StatementContext)_localctx).opa = operand();
				}
				break;
			}
			setState(37);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(36);
				((StatementContext)_localctx).opb = operand();
				}
				break;
			}
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARROW) {
				{
				setState(39);
				match(ARROW);
				setState(40);
				match(REGISTER);
				}
			}

			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(43);
				match(T__0);
				setState(44);
				((StatementContext)_localctx).predicate = number();
				setState(45);
				match(T__1);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LabeldefContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LabeldefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeldef; }
	}

	public final LabeldefContext labeldef() throws RecognitionException {
		LabeldefContext _localctx = new LabeldefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_labeldef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			label();
			setState(50);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LabelContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(AssemblyParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(AssemblyParser.ID, i);
		}
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_label);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(52);
				match(T__3);
				}
			}

			setState(55);
			match(ID);
			setState(60);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(56);
					match(T__3);
					setState(57);
					match(ID);
					}
					} 
				}
				setState(62);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OperandContext extends ParserRuleContext {
		public DirectOperandContext directOperand() {
			return getRuleContext(DirectOperandContext.class,0);
		}
		public IndirectOperandContext indirectOperand() {
			return getRuleContext(IndirectOperandContext.class,0);
		}
		public OperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operand; }
	}

	public final OperandContext operand() throws RecognitionException {
		OperandContext _localctx = new OperandContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_operand);
		try {
			setState(65);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__5:
			case T__6:
			case T__8:
			case T__9:
			case T__10:
			case T__15:
			case HEX_NUMBER:
			case BIN_NUMBER:
			case DEC_NUMBER:
			case REGISTER:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(63);
				directOperand();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				indirectOperand();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndirectOperandContext extends ParserRuleContext {
		public DirectOperandContext directOperand() {
			return getRuleContext(DirectOperandContext.class,0);
		}
		public IndirectOperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indirectOperand; }
	}

	public final IndirectOperandContext indirectOperand() throws RecognitionException {
		IndirectOperandContext _localctx = new IndirectOperandContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_indirectOperand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__4);
			setState(68);
			directOperand();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DirectOperandContext extends ParserRuleContext {
		public LiteralContext val;
		public Token reg;
		public IoOperandContext io;
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode REGISTER() { return getToken(AssemblyParser.REGISTER, 0); }
		public IoOperandContext ioOperand() {
			return getRuleContext(IoOperandContext.class,0);
		}
		public DirectOperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directOperand; }
	}

	public final DirectOperandContext directOperand() throws RecognitionException {
		DirectOperandContext _localctx = new DirectOperandContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_directOperand);
		try {
			setState(73);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__6:
			case T__8:
			case T__9:
			case T__10:
			case T__15:
			case HEX_NUMBER:
			case BIN_NUMBER:
			case DEC_NUMBER:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				((DirectOperandContext)_localctx).val = literal();
				}
				break;
			case REGISTER:
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				((DirectOperandContext)_localctx).reg = match(REGISTER);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				((DirectOperandContext)_localctx).io = ioOperand();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IoOperandContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public IoOperandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ioOperand; }
	}

	public final IoOperandContext ioOperand() throws RecognitionException {
		IoOperandContext _localctx = new IoOperandContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ioOperand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(T__5);
			setState(76);
			literal();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public ConstantExpressionContext constantExpression() {
			return getRuleContext(ConstantExpressionContext.class,0);
		}
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_literal);
		try {
			int _alt;
			setState(100);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
			case T__15:
			case HEX_NUMBER:
			case BIN_NUMBER:
			case DEC_NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				constantExpression(0);
				}
				break;
			case T__3:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				label();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				match(T__6);
				setState(81);
				label();
				setState(82);
				match(T__7);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				match(T__8);
				setState(88);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(85);
						matchWildcard();
						}
						} 
					}
					setState(90);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				setState(91);
				match(T__8);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 5);
				{
				setState(92);
				match(T__9);
				setState(96);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(93);
						matchWildcard();
						}
						} 
					}
					setState(98);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				setState(99);
				match(T__9);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantExpressionContext extends ParserRuleContext {
		public Token op;
		public List<ConstantExpressionContext> constantExpression() {
			return getRuleContexts(ConstantExpressionContext.class);
		}
		public ConstantExpressionContext constantExpression(int i) {
			return getRuleContext(ConstantExpressionContext.class,i);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ConstantExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantExpression; }
	}

	public final ConstantExpressionContext constantExpression() throws RecognitionException {
		return constantExpression(0);
	}

	private ConstantExpressionContext constantExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConstantExpressionContext _localctx = new ConstantExpressionContext(_ctx, _parentState);
		ConstantExpressionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_constantExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				{
				setState(103);
				match(T__10);
				setState(104);
				constantExpression(0);
				setState(105);
				match(T__11);
				}
				break;
			case T__15:
			case HEX_NUMBER:
			case BIN_NUMBER:
			case DEC_NUMBER:
				{
				setState(107);
				number();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(119);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ConstantExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_constantExpression);
						setState(110);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(111);
						((ConstantExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__12 || _la==T__13) ) {
							((ConstantExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(112);
						constantExpression(5);
						}
						break;
					case 2:
						{
						_localctx = new ConstantExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_constantExpression);
						setState(113);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(114);
						((ConstantExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__14 || _la==T__15) ) {
							((ConstantExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(115);
						constantExpression(4);
						}
						break;
					case 3:
						{
						_localctx = new ConstantExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_constantExpression);
						setState(116);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(117);
						((ConstantExpressionContext)_localctx).op = match(T__16);
						setState(118);
						constantExpression(3);
						}
						break;
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public TerminalNode HEX_NUMBER() { return getToken(AssemblyParser.HEX_NUMBER, 0); }
		public TerminalNode BIN_NUMBER() { return getToken(AssemblyParser.BIN_NUMBER, 0); }
		public TerminalNode DEC_NUMBER() { return getToken(AssemblyParser.DEC_NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(124);
				match(T__15);
				}
			}

			setState(127);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1835008L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return constantExpression_sempred((ConstantExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean constantExpression_sempred(ConstantExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001b\u0082\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001#\b\u0001\u0001"+
		"\u0001\u0003\u0001&\b\u0001\u0001\u0001\u0001\u0001\u0003\u0001*\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u00010\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u00036\b\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003;\b\u0003\n\u0003\f\u0003"+
		">\t\u0003\u0001\u0004\u0001\u0004\u0003\u0004B\b\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006J\b"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\bW\b\b\n\b\f\bZ\t\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b_\b\b\n\b\f\bb\t\b\u0001\b\u0003\be\b\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\tm\b\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\tx\b\t\n\t\f"+
		"\t{\t\t\u0001\n\u0003\n~\b\n\u0001\n\u0001\n\u0001\n\u0002X`\u0001\u0012"+
		"\u000b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0000\u0003"+
		"\u0001\u0000\r\u000e\u0001\u0000\u000f\u0010\u0001\u0000\u0012\u0014\u008d"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0002 \u0001\u0000\u0000\u0000\u0004"+
		"1\u0001\u0000\u0000\u0000\u00065\u0001\u0000\u0000\u0000\bA\u0001\u0000"+
		"\u0000\u0000\nC\u0001\u0000\u0000\u0000\fI\u0001\u0000\u0000\u0000\u000e"+
		"K\u0001\u0000\u0000\u0000\u0010d\u0001\u0000\u0000\u0000\u0012l\u0001"+
		"\u0000\u0000\u0000\u0014}\u0001\u0000\u0000\u0000\u0016\u001a\u0003\u0002"+
		"\u0001\u0000\u0017\u001a\u0003\u0004\u0002\u0000\u0018\u001a\u0003\u0010"+
		"\b\u0000\u0019\u0016\u0001\u0000\u0000\u0000\u0019\u0017\u0001\u0000\u0000"+
		"\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000"+
		"\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000"+
		"\u0000\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000"+
		"\u0000\u001e\u001f\u0005\u0000\u0000\u0001\u001f\u0001\u0001\u0000\u0000"+
		"\u0000 \"\u0005\u0017\u0000\u0000!#\u0003\b\u0004\u0000\"!\u0001\u0000"+
		"\u0000\u0000\"#\u0001\u0000\u0000\u0000#%\u0001\u0000\u0000\u0000$&\u0003"+
		"\b\u0004\u0000%$\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&)\u0001"+
		"\u0000\u0000\u0000\'(\u0005\u0015\u0000\u0000(*\u0005\u0016\u0000\u0000"+
		")\'\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*/\u0001\u0000\u0000"+
		"\u0000+,\u0005\u0001\u0000\u0000,-\u0003\u0014\n\u0000-.\u0005\u0002\u0000"+
		"\u0000.0\u0001\u0000\u0000\u0000/+\u0001\u0000\u0000\u0000/0\u0001\u0000"+
		"\u0000\u00000\u0003\u0001\u0000\u0000\u000012\u0003\u0006\u0003\u0000"+
		"23\u0005\u0003\u0000\u00003\u0005\u0001\u0000\u0000\u000046\u0005\u0004"+
		"\u0000\u000054\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u000067\u0001"+
		"\u0000\u0000\u00007<\u0005\u0018\u0000\u000089\u0005\u0004\u0000\u0000"+
		"9;\u0005\u0018\u0000\u0000:8\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000"+
		"\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=\u0007\u0001"+
		"\u0000\u0000\u0000><\u0001\u0000\u0000\u0000?B\u0003\f\u0006\u0000@B\u0003"+
		"\n\u0005\u0000A?\u0001\u0000\u0000\u0000A@\u0001\u0000\u0000\u0000B\t"+
		"\u0001\u0000\u0000\u0000CD\u0005\u0005\u0000\u0000DE\u0003\f\u0006\u0000"+
		"E\u000b\u0001\u0000\u0000\u0000FJ\u0003\u0010\b\u0000GJ\u0005\u0016\u0000"+
		"\u0000HJ\u0003\u000e\u0007\u0000IF\u0001\u0000\u0000\u0000IG\u0001\u0000"+
		"\u0000\u0000IH\u0001\u0000\u0000\u0000J\r\u0001\u0000\u0000\u0000KL\u0005"+
		"\u0006\u0000\u0000LM\u0003\u0010\b\u0000M\u000f\u0001\u0000\u0000\u0000"+
		"Ne\u0003\u0012\t\u0000Oe\u0003\u0006\u0003\u0000PQ\u0005\u0007\u0000\u0000"+
		"QR\u0003\u0006\u0003\u0000RS\u0005\b\u0000\u0000Se\u0001\u0000\u0000\u0000"+
		"TX\u0005\t\u0000\u0000UW\t\u0000\u0000\u0000VU\u0001\u0000\u0000\u0000"+
		"WZ\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000"+
		"\u0000Y[\u0001\u0000\u0000\u0000ZX\u0001\u0000\u0000\u0000[e\u0005\t\u0000"+
		"\u0000\\`\u0005\n\u0000\u0000]_\t\u0000\u0000\u0000^]\u0001\u0000\u0000"+
		"\u0000_b\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000`^\u0001\u0000"+
		"\u0000\u0000ac\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000ce\u0005"+
		"\n\u0000\u0000dN\u0001\u0000\u0000\u0000dO\u0001\u0000\u0000\u0000dP\u0001"+
		"\u0000\u0000\u0000dT\u0001\u0000\u0000\u0000d\\\u0001\u0000\u0000\u0000"+
		"e\u0011\u0001\u0000\u0000\u0000fg\u0006\t\uffff\uffff\u0000gh\u0005\u000b"+
		"\u0000\u0000hi\u0003\u0012\t\u0000ij\u0005\f\u0000\u0000jm\u0001\u0000"+
		"\u0000\u0000km\u0003\u0014\n\u0000lf\u0001\u0000\u0000\u0000lk\u0001\u0000"+
		"\u0000\u0000my\u0001\u0000\u0000\u0000no\n\u0004\u0000\u0000op\u0007\u0000"+
		"\u0000\u0000px\u0003\u0012\t\u0005qr\n\u0003\u0000\u0000rs\u0007\u0001"+
		"\u0000\u0000sx\u0003\u0012\t\u0004tu\n\u0002\u0000\u0000uv\u0005\u0011"+
		"\u0000\u0000vx\u0003\u0012\t\u0003wn\u0001\u0000\u0000\u0000wq\u0001\u0000"+
		"\u0000\u0000wt\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001"+
		"\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z\u0013\u0001\u0000\u0000"+
		"\u0000{y\u0001\u0000\u0000\u0000|~\u0005\u0010\u0000\u0000}|\u0001\u0000"+
		"\u0000\u0000}~\u0001\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000"+
		"\u007f\u0080\u0007\u0002\u0000\u0000\u0080\u0015\u0001\u0000\u0000\u0000"+
		"\u0011\u0019\u001b\"%)/5<AIX`dlwy}";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}