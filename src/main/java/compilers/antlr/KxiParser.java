// Generated from /home/nag/Documents/UVU/Spring2024/Compiler/CompilerJava/src/main/java/compilers/antlr/KxiParser.g4 by ANTLR 4.13.1
package compilers.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class KxiParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, BREAK=2, CASE=3, CLASS=4, CHAR=5, CIN=6, COUT=7, DEFAULT=8, ELSE=9, 
		FALSE=10, FOR=11, IF=12, INT=13, NEW=14, NULL=15, PUBLIC=16, PRIVATE=17, 
		RETURN=18, STATIC=19, STRING=20, SWITCH=21, THIS=22, TRUE=23, VOID=24, 
		WHILE=25, SEMICOLON=26, LCURLY=27, RCURLY=28, LPAREN=29, RPAREN=30, LSQUARE=31, 
		RSQUARE=32, EQ=33, EQEQ=34, NOTEQ=35, GEQ=36, LEQ=37, LT=38, GT=39, AND=40, 
		OR=41, NOT=42, PLUS=43, MINUS=44, TIMES=45, DIVIDE=46, PLUSEQ=47, MINUSEQ=48, 
		TIMESEQ=49, DIVIDEEQ=50, EXTRACT=51, INSERT=52, DOT=53, COMMA=54, COLON=55, 
		ID=56, CHARLIT=57, STRINGLIT=58, INTLIT=59, COMMENT=60, MULTI_COMMENT=61, 
		WS=62, NEWLINE=63, UNKNOWN=64;
	public static final int
		RULE_compilationUnit = 0, RULE_classDefinition = 1, RULE_scalarType = 2, 
		RULE_type = 3, RULE_modifier = 4, RULE_classMemberDefinition = 5, RULE_dataMemberDeclaration = 6, 
		RULE_methodDeclaration = 7, RULE_constructorDeclaration = 8, RULE_methodSuffix = 9, 
		RULE_parameterList = 10, RULE_parameter = 11, RULE_variableDeclaration = 12, 
		RULE_initializer = 13, RULE_statement = 14, RULE_block = 15, RULE_caseBlock = 16, 
		RULE_case = 17, RULE_expression = 18, RULE_arguments = 19, RULE_argumentList = 20, 
		RULE_index = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "classDefinition", "scalarType", "type", "modifier", 
			"classMemberDefinition", "dataMemberDeclaration", "methodDeclaration", 
			"constructorDeclaration", "methodSuffix", "parameterList", "parameter", 
			"variableDeclaration", "initializer", "statement", "block", "caseBlock", 
			"case", "expression", "arguments", "argumentList", "index"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "';'", "'{'", "'}'", "'('", "')'", "'['", "']'", "'='", null, 
			null, null, null, "'<'", "'>'", null, null, "'!'", "'+'", "'-'", "'*'", 
			"'/'", null, null, null, null, null, null, "'.'", "','", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BOOL", "BREAK", "CASE", "CLASS", "CHAR", "CIN", "COUT", "DEFAULT", 
			"ELSE", "FALSE", "FOR", "IF", "INT", "NEW", "NULL", "PUBLIC", "PRIVATE", 
			"RETURN", "STATIC", "STRING", "SWITCH", "THIS", "TRUE", "VOID", "WHILE", 
			"SEMICOLON", "LCURLY", "RCURLY", "LPAREN", "RPAREN", "LSQUARE", "RSQUARE", 
			"EQ", "EQEQ", "NOTEQ", "GEQ", "LEQ", "LT", "GT", "AND", "OR", "NOT", 
			"PLUS", "MINUS", "TIMES", "DIVIDE", "PLUSEQ", "MINUSEQ", "TIMESEQ", "DIVIDEEQ", 
			"EXTRACT", "INSERT", "DOT", "COMMA", "COLON", "ID", "CHARLIT", "STRINGLIT", 
			"INTLIT", "COMMENT", "MULTI_COMMENT", "WS", "NEWLINE", "UNKNOWN"
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
	public String getGrammarFileName() { return "KxiParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public KxiParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompilationUnitContext extends ParserRuleContext {
		public TerminalNode VOID() { return getToken(KxiParser.VOID, 0); }
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(KxiParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(KxiParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<ClassDefinitionContext> classDefinition() {
			return getRuleContexts(ClassDefinitionContext.class);
		}
		public ClassDefinitionContext classDefinition(int i) {
			return getRuleContext(ClassDefinitionContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CLASS) {
				{
				{
				setState(44);
				classDefinition();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50);
			match(VOID);
			setState(51);
			match(ID);
			setState(52);
			match(LPAREN);
			setState(53);
			match(RPAREN);
			setState(54);
			block();
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
	public static class ClassDefinitionContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(KxiParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public TerminalNode LCURLY() { return getToken(KxiParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(KxiParser.RCURLY, 0); }
		public List<ClassMemberDefinitionContext> classMemberDefinition() {
			return getRuleContexts(ClassMemberDefinitionContext.class);
		}
		public ClassMemberDefinitionContext classMemberDefinition(int i) {
			return getRuleContext(ClassMemberDefinitionContext.class,i);
		}
		public ClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitClassDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefinitionContext classDefinition() throws RecognitionException {
		ClassDefinitionContext _localctx = new ClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(CLASS);
			setState(57);
			match(ID);
			setState(58);
			match(LCURLY);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 72057594038648832L) != 0)) {
				{
				{
				setState(59);
				classMemberDefinition();
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(65);
			match(RCURLY);
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
	public static class ScalarTypeContext extends ParserRuleContext {
		public TerminalNode VOID() { return getToken(KxiParser.VOID, 0); }
		public TerminalNode INT() { return getToken(KxiParser.INT, 0); }
		public TerminalNode CHAR() { return getToken(KxiParser.CHAR, 0); }
		public TerminalNode BOOL() { return getToken(KxiParser.BOOL, 0); }
		public TerminalNode STRING() { return getToken(KxiParser.STRING, 0); }
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public ScalarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scalarType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitScalarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScalarTypeContext scalarType() throws RecognitionException {
		ScalarTypeContext _localctx = new ScalarTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_scalarType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 72057594055761954L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public ScalarTypeContext scalarType() {
			return getRuleContext(ScalarTypeContext.class,0);
		}
		public List<TerminalNode> LSQUARE() { return getTokens(KxiParser.LSQUARE); }
		public TerminalNode LSQUARE(int i) {
			return getToken(KxiParser.LSQUARE, i);
		}
		public List<TerminalNode> RSQUARE() { return getTokens(KxiParser.RSQUARE); }
		public TerminalNode RSQUARE(int i) {
			return getToken(KxiParser.RSQUARE, i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			scalarType();
			setState(74);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(70);
					match(LSQUARE);
					setState(71);
					match(RSQUARE);
					}
					} 
				}
				setState(76);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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
	public static class ModifierContext extends ParserRuleContext {
		public TerminalNode PUBLIC() { return getToken(KxiParser.PUBLIC, 0); }
		public TerminalNode PRIVATE() { return getToken(KxiParser.PRIVATE, 0); }
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_modifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			_la = _input.LA(1);
			if ( !(_la==PUBLIC || _la==PRIVATE) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class ClassMemberDefinitionContext extends ParserRuleContext {
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public DataMemberDeclarationContext dataMemberDeclaration() {
			return getRuleContext(DataMemberDeclarationContext.class,0);
		}
		public ConstructorDeclarationContext constructorDeclaration() {
			return getRuleContext(ConstructorDeclarationContext.class,0);
		}
		public ClassMemberDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classMemberDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitClassMemberDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassMemberDefinitionContext classMemberDefinition() throws RecognitionException {
		ClassMemberDefinitionContext _localctx = new ClassMemberDefinitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classMemberDefinition);
		try {
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(79);
				methodDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				dataMemberDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(81);
				constructorDeclaration();
				}
				break;
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
	public static class DataMemberDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier() {
			return getRuleContext(ModifierContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public TerminalNode STATIC() { return getToken(KxiParser.STATIC, 0); }
		public DataMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataMemberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitDataMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataMemberDeclarationContext dataMemberDeclaration() throws RecognitionException {
		DataMemberDeclarationContext _localctx = new DataMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_dataMemberDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STATIC) {
				{
				setState(84);
				match(STATIC);
				}
			}

			setState(87);
			modifier();
			setState(88);
			variableDeclaration();
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
	public static class MethodDeclarationContext extends ParserRuleContext {
		public ModifierContext modifier() {
			return getRuleContext(ModifierContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public MethodSuffixContext methodSuffix() {
			return getRuleContext(MethodSuffixContext.class,0);
		}
		public TerminalNode STATIC() { return getToken(KxiParser.STATIC, 0); }
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STATIC) {
				{
				setState(90);
				match(STATIC);
				}
			}

			setState(93);
			modifier();
			setState(94);
			type();
			setState(95);
			methodSuffix();
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
	public static class ConstructorDeclarationContext extends ParserRuleContext {
		public MethodSuffixContext methodSuffix() {
			return getRuleContext(MethodSuffixContext.class,0);
		}
		public ConstructorDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitConstructorDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDeclarationContext constructorDeclaration() throws RecognitionException {
		ConstructorDeclarationContext _localctx = new ConstructorDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constructorDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			methodSuffix();
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
	public static class MethodSuffixContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(KxiParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(KxiParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public MethodSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodSuffix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitMethodSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodSuffixContext methodSuffix() throws RecognitionException {
		MethodSuffixContext _localctx = new MethodSuffixContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_methodSuffix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(ID);
			setState(100);
			match(LPAREN);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 72057594055761954L) != 0)) {
				{
				setState(101);
				parameterList();
				}
			}

			setState(104);
			match(RPAREN);
			setState(105);
			block();
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
	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(KxiParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(KxiParser.COMMA, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			parameter();
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(108);
				match(COMMA);
				setState(109);
				parameter();
				}
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class ParameterContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			type();
			setState(116);
			match(ID);
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
	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(KxiParser.SEMICOLON, 0); }
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			type();
			setState(119);
			match(ID);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQ) {
				{
				setState(120);
				initializer();
				}
			}

			setState(123);
			match(SEMICOLON);
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
	public static class InitializerContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(KxiParser.EQ, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_initializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(EQ);
			setState(126);
			expression(0);
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
		public TerminalNode IF() { return getToken(KxiParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(KxiParser.LPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(KxiParser.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(KxiParser.ELSE, 0); }
		public TerminalNode WHILE() { return getToken(KxiParser.WHILE, 0); }
		public TerminalNode FOR() { return getToken(KxiParser.FOR, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(KxiParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(KxiParser.SEMICOLON, i);
		}
		public TerminalNode RETURN() { return getToken(KxiParser.RETURN, 0); }
		public TerminalNode COUT() { return getToken(KxiParser.COUT, 0); }
		public TerminalNode EXTRACT() { return getToken(KxiParser.EXTRACT, 0); }
		public TerminalNode CIN() { return getToken(KxiParser.CIN, 0); }
		public TerminalNode INSERT() { return getToken(KxiParser.INSERT, 0); }
		public TerminalNode SWITCH() { return getToken(KxiParser.SWITCH, 0); }
		public CaseBlockContext caseBlock() {
			return getRuleContext(CaseBlockContext.class,0);
		}
		public TerminalNode BREAK() { return getToken(KxiParser.BREAK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statement);
		int _la;
		try {
			setState(185);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				match(IF);
				setState(129);
				match(LPAREN);
				setState(130);
				expression(0);
				setState(131);
				match(RPAREN);
				setState(132);
				statement();
				setState(135);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(133);
					match(ELSE);
					setState(134);
					statement();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(137);
				match(WHILE);
				setState(138);
				match(LPAREN);
				setState(139);
				expression(0);
				setState(140);
				match(RPAREN);
				setState(141);
				statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(143);
				match(FOR);
				setState(144);
				match(LPAREN);
				setState(146);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697444000768L) != 0)) {
					{
					setState(145);
					expression(0);
					}
				}

				setState(148);
				match(SEMICOLON);
				setState(149);
				expression(0);
				setState(150);
				match(SEMICOLON);
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697444000768L) != 0)) {
					{
					setState(151);
					expression(0);
					}
				}

				setState(154);
				match(RPAREN);
				setState(155);
				statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(157);
				match(RETURN);
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697444000768L) != 0)) {
					{
					setState(158);
					expression(0);
					}
				}

				setState(161);
				match(SEMICOLON);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(162);
				match(COUT);
				setState(163);
				match(EXTRACT);
				setState(164);
				expression(0);
				setState(165);
				match(SEMICOLON);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(167);
				match(CIN);
				setState(168);
				match(INSERT);
				setState(169);
				expression(0);
				setState(170);
				match(SEMICOLON);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(172);
				match(SWITCH);
				setState(173);
				match(LPAREN);
				setState(174);
				expression(0);
				setState(175);
				match(RPAREN);
				setState(176);
				caseBlock();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(178);
				match(BREAK);
				setState(179);
				match(SEMICOLON);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(180);
				expression(0);
				setState(181);
				match(SEMICOLON);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(183);
				block();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(184);
				variableDeclaration();
				}
				break;
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
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(KxiParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(KxiParser.RCURLY, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(LCURLY);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697631972582L) != 0)) {
				{
				{
				setState(188);
				statement();
				}
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(194);
			match(RCURLY);
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
	public static class CaseBlockContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(KxiParser.LCURLY, 0); }
		public TerminalNode DEFAULT() { return getToken(KxiParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(KxiParser.COLON, 0); }
		public TerminalNode RCURLY() { return getToken(KxiParser.RCURLY, 0); }
		public List<CaseContext> case_() {
			return getRuleContexts(CaseContext.class);
		}
		public CaseContext case_(int i) {
			return getRuleContext(CaseContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CaseBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitCaseBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseBlockContext caseBlock() throws RecognitionException {
		CaseBlockContext _localctx = new CaseBlockContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_caseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(LCURLY);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(197);
				case_();
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(203);
			match(DEFAULT);
			setState(204);
			match(COLON);
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697631972582L) != 0)) {
				{
				{
				setState(205);
				statement();
				}
				}
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(211);
			match(RCURLY);
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
	public static class CaseContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(KxiParser.CASE, 0); }
		public TerminalNode COLON() { return getToken(KxiParser.COLON, 0); }
		public TerminalNode INTLIT() { return getToken(KxiParser.INTLIT, 0); }
		public TerminalNode CHARLIT() { return getToken(KxiParser.CHARLIT, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_case; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseContext case_() throws RecognitionException {
		CaseContext _localctx = new CaseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_case);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(CASE);
			setState(214);
			_la = _input.LA(1);
			if ( !(_la==CHARLIT || _la==INTLIT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(215);
			match(COLON);
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697631972582L) != 0)) {
				{
				{
				setState(216);
				statement();
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(KxiParser.LPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(KxiParser.RPAREN, 0); }
		public TerminalNode NEW() { return getToken(KxiParser.NEW, 0); }
		public TerminalNode ID() { return getToken(KxiParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public TerminalNode NOT() { return getToken(KxiParser.NOT, 0); }
		public TerminalNode PLUS() { return getToken(KxiParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(KxiParser.MINUS, 0); }
		public TerminalNode INTLIT() { return getToken(KxiParser.INTLIT, 0); }
		public TerminalNode CHARLIT() { return getToken(KxiParser.CHARLIT, 0); }
		public TerminalNode STRINGLIT() { return getToken(KxiParser.STRINGLIT, 0); }
		public TerminalNode TRUE() { return getToken(KxiParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(KxiParser.FALSE, 0); }
		public TerminalNode NULL() { return getToken(KxiParser.NULL, 0); }
		public TerminalNode THIS() { return getToken(KxiParser.THIS, 0); }
		public TerminalNode TIMES() { return getToken(KxiParser.TIMES, 0); }
		public TerminalNode DIVIDE() { return getToken(KxiParser.DIVIDE, 0); }
		public TerminalNode LT() { return getToken(KxiParser.LT, 0); }
		public TerminalNode GT() { return getToken(KxiParser.GT, 0); }
		public TerminalNode LEQ() { return getToken(KxiParser.LEQ, 0); }
		public TerminalNode GEQ() { return getToken(KxiParser.GEQ, 0); }
		public TerminalNode EQEQ() { return getToken(KxiParser.EQEQ, 0); }
		public TerminalNode NOTEQ() { return getToken(KxiParser.NOTEQ, 0); }
		public TerminalNode AND() { return getToken(KxiParser.AND, 0); }
		public TerminalNode OR() { return getToken(KxiParser.OR, 0); }
		public TerminalNode EQ() { return getToken(KxiParser.EQ, 0); }
		public TerminalNode PLUSEQ() { return getToken(KxiParser.PLUSEQ, 0); }
		public TerminalNode MINUSEQ() { return getToken(KxiParser.MINUSEQ, 0); }
		public TerminalNode TIMESEQ() { return getToken(KxiParser.TIMESEQ, 0); }
		public TerminalNode DIVIDEEQ() { return getToken(KxiParser.DIVIDEEQ, 0); }
		public TerminalNode DOT() { return getToken(KxiParser.DOT, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(223);
				match(LPAREN);
				setState(224);
				expression(0);
				setState(225);
				match(RPAREN);
				}
				break;
			case 2:
				{
				setState(227);
				match(NEW);
				setState(228);
				match(ID);
				setState(229);
				arguments();
				}
				break;
			case 3:
				{
				setState(230);
				match(NEW);
				setState(231);
				type();
				setState(232);
				index();
				}
				break;
			case 4:
				{
				setState(234);
				match(NOT);
				setState(235);
				expression(18);
				}
				break;
			case 5:
				{
				setState(236);
				match(PLUS);
				setState(237);
				expression(17);
				}
				break;
			case 6:
				{
				setState(238);
				match(MINUS);
				setState(239);
				expression(16);
				}
				break;
			case 7:
				{
				setState(240);
				match(INTLIT);
				}
				break;
			case 8:
				{
				setState(241);
				match(CHARLIT);
				}
				break;
			case 9:
				{
				setState(242);
				match(STRINGLIT);
				}
				break;
			case 10:
				{
				setState(243);
				match(TRUE);
				}
				break;
			case 11:
				{
				setState(244);
				match(FALSE);
				}
				break;
			case 12:
				{
				setState(245);
				match(NULL);
				}
				break;
			case 13:
				{
				setState(246);
				match(THIS);
				}
				break;
			case 14:
				{
				setState(247);
				match(ID);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(280);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(278);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(250);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(251);
						_la = _input.LA(1);
						if ( !(_la==TIMES || _la==DIVIDE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(252);
						expression(16);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(253);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(254);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(255);
						expression(15);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(256);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(257);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1030792151040L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(258);
						expression(14);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(259);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(260);
						_la = _input.LA(1);
						if ( !(_la==EQEQ || _la==NOTEQ) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(261);
						expression(13);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(262);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(263);
						match(AND);
						setState(264);
						expression(12);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(265);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(266);
						match(OR);
						setState(267);
						expression(11);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(268);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(269);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2111070915264512L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(270);
						expression(9);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(271);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(272);
						index();
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(273);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(274);
						arguments();
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(275);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(276);
						match(DOT);
						setState(277);
						match(ID);
						}
						break;
					}
					} 
				}
				setState(282);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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
	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(KxiParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(KxiParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(LPAREN);
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1080894697444000768L) != 0)) {
				{
				setState(284);
				argumentList();
				}
			}

			setState(287);
			match(RPAREN);
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
	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(KxiParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(KxiParser.COMMA, i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(289);
			expression(0);
			setState(294);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(290);
				match(COMMA);
				setState(291);
				expression(0);
				}
				}
				setState(296);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
	public static class IndexContext extends ParserRuleContext {
		public TerminalNode LSQUARE() { return getToken(KxiParser.LSQUARE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RSQUARE() { return getToken(KxiParser.RSQUARE, 0); }
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KxiParserVisitor ) return ((KxiParserVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(LSQUARE);
			setState(298);
			expression(0);
			setState(299);
			match(RSQUARE);
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
		case 18:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 15);
		case 1:
			return precpred(_ctx, 14);
		case 2:
			return precpred(_ctx, 13);
		case 3:
			return precpred(_ctx, 12);
		case 4:
			return precpred(_ctx, 11);
		case 5:
			return precpred(_ctx, 10);
		case 6:
			return precpred(_ctx, 9);
		case 7:
			return precpred(_ctx, 23);
		case 8:
			return precpred(_ctx, 22);
		case 9:
			return precpred(_ctx, 21);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001@\u012e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0001\u0000\u0005\u0000.\b\u0000\n\u0000\f\u00001\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001=\b\u0001\n\u0001\f\u0001"+
		"@\t\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003I\b\u0003\n\u0003\f\u0003L\t\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"S\b\u0005\u0001\u0006\u0003\u0006V\b\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0003\u0007\\\b\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0003\tg\b"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0005\no\b\n\n\n\f"+
		"\nr\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0003"+
		"\fz\b\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u0088\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0093\b\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0099\b\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00a0\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u00ba\b\u000e\u0001\u000f\u0001\u000f\u0005\u000f"+
		"\u00be\b\u000f\n\u000f\f\u000f\u00c1\t\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0005\u0010\u00c7\b\u0010\n\u0010\f\u0010\u00ca\t\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00cf\b\u0010\n\u0010"+
		"\f\u0010\u00d2\t\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0005\u0011\u00da\b\u0011\n\u0011\f\u0011\u00dd"+
		"\t\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00f9\b\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u0117\b\u0012\n\u0012\f\u0012"+
		"\u011a\t\u0012\u0001\u0013\u0001\u0013\u0003\u0013\u011e\b\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u0125"+
		"\b\u0014\n\u0014\f\u0014\u0128\t\u0014\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0000\u0001$\u0016\u0000\u0002\u0004\u0006\b"+
		"\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*\u0000"+
		"\b\u0006\u0000\u0001\u0001\u0005\u0005\r\r\u0014\u0014\u0018\u001888\u0001"+
		"\u0000\u0010\u0011\u0002\u000099;;\u0001\u0000-.\u0001\u0000+,\u0001\u0000"+
		"$\'\u0001\u0000\"#\u0002\u0000!!/2\u014c\u0000/\u0001\u0000\u0000\u0000"+
		"\u00028\u0001\u0000\u0000\u0000\u0004C\u0001\u0000\u0000\u0000\u0006E"+
		"\u0001\u0000\u0000\u0000\bM\u0001\u0000\u0000\u0000\nR\u0001\u0000\u0000"+
		"\u0000\fU\u0001\u0000\u0000\u0000\u000e[\u0001\u0000\u0000\u0000\u0010"+
		"a\u0001\u0000\u0000\u0000\u0012c\u0001\u0000\u0000\u0000\u0014k\u0001"+
		"\u0000\u0000\u0000\u0016s\u0001\u0000\u0000\u0000\u0018v\u0001\u0000\u0000"+
		"\u0000\u001a}\u0001\u0000\u0000\u0000\u001c\u00b9\u0001\u0000\u0000\u0000"+
		"\u001e\u00bb\u0001\u0000\u0000\u0000 \u00c4\u0001\u0000\u0000\u0000\""+
		"\u00d5\u0001\u0000\u0000\u0000$\u00f8\u0001\u0000\u0000\u0000&\u011b\u0001"+
		"\u0000\u0000\u0000(\u0121\u0001\u0000\u0000\u0000*\u0129\u0001\u0000\u0000"+
		"\u0000,.\u0003\u0002\u0001\u0000-,\u0001\u0000\u0000\u0000.1\u0001\u0000"+
		"\u0000\u0000/-\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000\u000002\u0001"+
		"\u0000\u0000\u00001/\u0001\u0000\u0000\u000023\u0005\u0018\u0000\u0000"+
		"34\u00058\u0000\u000045\u0005\u001d\u0000\u000056\u0005\u001e\u0000\u0000"+
		"67\u0003\u001e\u000f\u00007\u0001\u0001\u0000\u0000\u000089\u0005\u0004"+
		"\u0000\u00009:\u00058\u0000\u0000:>\u0005\u001b\u0000\u0000;=\u0003\n"+
		"\u0005\u0000<;\u0001\u0000\u0000\u0000=@\u0001\u0000\u0000\u0000><\u0001"+
		"\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?A\u0001\u0000\u0000\u0000"+
		"@>\u0001\u0000\u0000\u0000AB\u0005\u001c\u0000\u0000B\u0003\u0001\u0000"+
		"\u0000\u0000CD\u0007\u0000\u0000\u0000D\u0005\u0001\u0000\u0000\u0000"+
		"EJ\u0003\u0004\u0002\u0000FG\u0005\u001f\u0000\u0000GI\u0005 \u0000\u0000"+
		"HF\u0001\u0000\u0000\u0000IL\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000"+
		"\u0000JK\u0001\u0000\u0000\u0000K\u0007\u0001\u0000\u0000\u0000LJ\u0001"+
		"\u0000\u0000\u0000MN\u0007\u0001\u0000\u0000N\t\u0001\u0000\u0000\u0000"+
		"OS\u0003\u000e\u0007\u0000PS\u0003\f\u0006\u0000QS\u0003\u0010\b\u0000"+
		"RO\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RQ\u0001\u0000\u0000"+
		"\u0000S\u000b\u0001\u0000\u0000\u0000TV\u0005\u0013\u0000\u0000UT\u0001"+
		"\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000"+
		"WX\u0003\b\u0004\u0000XY\u0003\u0018\f\u0000Y\r\u0001\u0000\u0000\u0000"+
		"Z\\\u0005\u0013\u0000\u0000[Z\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000"+
		"\u0000\\]\u0001\u0000\u0000\u0000]^\u0003\b\u0004\u0000^_\u0003\u0006"+
		"\u0003\u0000_`\u0003\u0012\t\u0000`\u000f\u0001\u0000\u0000\u0000ab\u0003"+
		"\u0012\t\u0000b\u0011\u0001\u0000\u0000\u0000cd\u00058\u0000\u0000df\u0005"+
		"\u001d\u0000\u0000eg\u0003\u0014\n\u0000fe\u0001\u0000\u0000\u0000fg\u0001"+
		"\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000hi\u0005\u001e\u0000\u0000"+
		"ij\u0003\u001e\u000f\u0000j\u0013\u0001\u0000\u0000\u0000kp\u0003\u0016"+
		"\u000b\u0000lm\u00056\u0000\u0000mo\u0003\u0016\u000b\u0000nl\u0001\u0000"+
		"\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000pq\u0001"+
		"\u0000\u0000\u0000q\u0015\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000"+
		"\u0000st\u0003\u0006\u0003\u0000tu\u00058\u0000\u0000u\u0017\u0001\u0000"+
		"\u0000\u0000vw\u0003\u0006\u0003\u0000wy\u00058\u0000\u0000xz\u0003\u001a"+
		"\r\u0000yx\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z{\u0001\u0000"+
		"\u0000\u0000{|\u0005\u001a\u0000\u0000|\u0019\u0001\u0000\u0000\u0000"+
		"}~\u0005!\u0000\u0000~\u007f\u0003$\u0012\u0000\u007f\u001b\u0001\u0000"+
		"\u0000\u0000\u0080\u0081\u0005\f\u0000\u0000\u0081\u0082\u0005\u001d\u0000"+
		"\u0000\u0082\u0083\u0003$\u0012\u0000\u0083\u0084\u0005\u001e\u0000\u0000"+
		"\u0084\u0087\u0003\u001c\u000e\u0000\u0085\u0086\u0005\t\u0000\u0000\u0086"+
		"\u0088\u0003\u001c\u000e\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0001\u0000\u0000\u0000\u0088\u00ba\u0001\u0000\u0000\u0000\u0089"+
		"\u008a\u0005\u0019\u0000\u0000\u008a\u008b\u0005\u001d\u0000\u0000\u008b"+
		"\u008c\u0003$\u0012\u0000\u008c\u008d\u0005\u001e\u0000\u0000\u008d\u008e"+
		"\u0003\u001c\u000e\u0000\u008e\u00ba\u0001\u0000\u0000\u0000\u008f\u0090"+
		"\u0005\u000b\u0000\u0000\u0090\u0092\u0005\u001d\u0000\u0000\u0091\u0093"+
		"\u0003$\u0012\u0000\u0092\u0091\u0001\u0000\u0000\u0000\u0092\u0093\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0095\u0005"+
		"\u001a\u0000\u0000\u0095\u0096\u0003$\u0012\u0000\u0096\u0098\u0005\u001a"+
		"\u0000\u0000\u0097\u0099\u0003$\u0012\u0000\u0098\u0097\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000"+
		"\u0000\u009a\u009b\u0005\u001e\u0000\u0000\u009b\u009c\u0003\u001c\u000e"+
		"\u0000\u009c\u00ba\u0001\u0000\u0000\u0000\u009d\u009f\u0005\u0012\u0000"+
		"\u0000\u009e\u00a0\u0003$\u0012\u0000\u009f\u009e\u0001\u0000\u0000\u0000"+
		"\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000"+
		"\u00a1\u00ba\u0005\u001a\u0000\u0000\u00a2\u00a3\u0005\u0007\u0000\u0000"+
		"\u00a3\u00a4\u00053\u0000\u0000\u00a4\u00a5\u0003$\u0012\u0000\u00a5\u00a6"+
		"\u0005\u001a\u0000\u0000\u00a6\u00ba\u0001\u0000\u0000\u0000\u00a7\u00a8"+
		"\u0005\u0006\u0000\u0000\u00a8\u00a9\u00054\u0000\u0000\u00a9\u00aa\u0003"+
		"$\u0012\u0000\u00aa\u00ab\u0005\u001a\u0000\u0000\u00ab\u00ba\u0001\u0000"+
		"\u0000\u0000\u00ac\u00ad\u0005\u0015\u0000\u0000\u00ad\u00ae\u0005\u001d"+
		"\u0000\u0000\u00ae\u00af\u0003$\u0012\u0000\u00af\u00b0\u0005\u001e\u0000"+
		"\u0000\u00b0\u00b1\u0003 \u0010\u0000\u00b1\u00ba\u0001\u0000\u0000\u0000"+
		"\u00b2\u00b3\u0005\u0002\u0000\u0000\u00b3\u00ba\u0005\u001a\u0000\u0000"+
		"\u00b4\u00b5\u0003$\u0012\u0000\u00b5\u00b6\u0005\u001a\u0000\u0000\u00b6"+
		"\u00ba\u0001\u0000\u0000\u0000\u00b7\u00ba\u0003\u001e\u000f\u0000\u00b8"+
		"\u00ba\u0003\u0018\f\u0000\u00b9\u0080\u0001\u0000\u0000\u0000\u00b9\u0089"+
		"\u0001\u0000\u0000\u0000\u00b9\u008f\u0001\u0000\u0000\u0000\u00b9\u009d"+
		"\u0001\u0000\u0000\u0000\u00b9\u00a2\u0001\u0000\u0000\u0000\u00b9\u00a7"+
		"\u0001\u0000\u0000\u0000\u00b9\u00ac\u0001\u0000\u0000\u0000\u00b9\u00b2"+
		"\u0001\u0000\u0000\u0000\u00b9\u00b4\u0001\u0000\u0000\u0000\u00b9\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b9\u00b8\u0001\u0000\u0000\u0000\u00ba\u001d"+
		"\u0001\u0000\u0000\u0000\u00bb\u00bf\u0005\u001b\u0000\u0000\u00bc\u00be"+
		"\u0003\u001c\u000e\u0000\u00bd\u00bc\u0001\u0000\u0000\u0000\u00be\u00c1"+
		"\u0001\u0000\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c0"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c2\u0001\u0000\u0000\u0000\u00c1\u00bf"+
		"\u0001\u0000\u0000\u0000\u00c2\u00c3\u0005\u001c\u0000\u0000\u00c3\u001f"+
		"\u0001\u0000\u0000\u0000\u00c4\u00c8\u0005\u001b\u0000\u0000\u00c5\u00c7"+
		"\u0003\"\u0011\u0000\u00c6\u00c5\u0001\u0000\u0000\u0000\u00c7\u00ca\u0001"+
		"\u0000\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001"+
		"\u0000\u0000\u0000\u00c9\u00cb\u0001\u0000\u0000\u0000\u00ca\u00c8\u0001"+
		"\u0000\u0000\u0000\u00cb\u00cc\u0005\b\u0000\u0000\u00cc\u00d0\u00057"+
		"\u0000\u0000\u00cd\u00cf\u0003\u001c\u000e\u0000\u00ce\u00cd\u0001\u0000"+
		"\u0000\u0000\u00cf\u00d2\u0001\u0000\u0000\u0000\u00d0\u00ce\u0001\u0000"+
		"\u0000\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u00d3\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d0\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005\u001c"+
		"\u0000\u0000\u00d4!\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005\u0003\u0000"+
		"\u0000\u00d6\u00d7\u0007\u0002\u0000\u0000\u00d7\u00db\u00057\u0000\u0000"+
		"\u00d8\u00da\u0003\u001c\u000e\u0000\u00d9\u00d8\u0001\u0000\u0000\u0000"+
		"\u00da\u00dd\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000"+
		"\u00db\u00dc\u0001\u0000\u0000\u0000\u00dc#\u0001\u0000\u0000\u0000\u00dd"+
		"\u00db\u0001\u0000\u0000\u0000\u00de\u00df\u0006\u0012\uffff\uffff\u0000"+
		"\u00df\u00e0\u0005\u001d\u0000\u0000\u00e0\u00e1\u0003$\u0012\u0000\u00e1"+
		"\u00e2\u0005\u001e\u0000\u0000\u00e2\u00f9\u0001\u0000\u0000\u0000\u00e3"+
		"\u00e4\u0005\u000e\u0000\u0000\u00e4\u00e5\u00058\u0000\u0000\u00e5\u00f9"+
		"\u0003&\u0013\u0000\u00e6\u00e7\u0005\u000e\u0000\u0000\u00e7\u00e8\u0003"+
		"\u0006\u0003\u0000\u00e8\u00e9\u0003*\u0015\u0000\u00e9\u00f9\u0001\u0000"+
		"\u0000\u0000\u00ea\u00eb\u0005*\u0000\u0000\u00eb\u00f9\u0003$\u0012\u0012"+
		"\u00ec\u00ed\u0005+\u0000\u0000\u00ed\u00f9\u0003$\u0012\u0011\u00ee\u00ef"+
		"\u0005,\u0000\u0000\u00ef\u00f9\u0003$\u0012\u0010\u00f0\u00f9\u0005;"+
		"\u0000\u0000\u00f1\u00f9\u00059\u0000\u0000\u00f2\u00f9\u0005:\u0000\u0000"+
		"\u00f3\u00f9\u0005\u0017\u0000\u0000\u00f4\u00f9\u0005\n\u0000\u0000\u00f5"+
		"\u00f9\u0005\u000f\u0000\u0000\u00f6\u00f9\u0005\u0016\u0000\u0000\u00f7"+
		"\u00f9\u00058\u0000\u0000\u00f8\u00de\u0001\u0000\u0000\u0000\u00f8\u00e3"+
		"\u0001\u0000\u0000\u0000\u00f8\u00e6\u0001\u0000\u0000\u0000\u00f8\u00ea"+
		"\u0001\u0000\u0000\u0000\u00f8\u00ec\u0001\u0000\u0000\u0000\u00f8\u00ee"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f0\u0001\u0000\u0000\u0000\u00f8\u00f1"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f2\u0001\u0000\u0000\u0000\u00f8\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f4\u0001\u0000\u0000\u0000\u00f8\u00f5"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f6\u0001\u0000\u0000\u0000\u00f8\u00f7"+
		"\u0001\u0000\u0000\u0000\u00f9\u0118\u0001\u0000\u0000\u0000\u00fa\u00fb"+
		"\n\u000f\u0000\u0000\u00fb\u00fc\u0007\u0003\u0000\u0000\u00fc\u0117\u0003"+
		"$\u0012\u0010\u00fd\u00fe\n\u000e\u0000\u0000\u00fe\u00ff\u0007\u0004"+
		"\u0000\u0000\u00ff\u0117\u0003$\u0012\u000f\u0100\u0101\n\r\u0000\u0000"+
		"\u0101\u0102\u0007\u0005\u0000\u0000\u0102\u0117\u0003$\u0012\u000e\u0103"+
		"\u0104\n\f\u0000\u0000\u0104\u0105\u0007\u0006\u0000\u0000\u0105\u0117"+
		"\u0003$\u0012\r\u0106\u0107\n\u000b\u0000\u0000\u0107\u0108\u0005(\u0000"+
		"\u0000\u0108\u0117\u0003$\u0012\f\u0109\u010a\n\n\u0000\u0000\u010a\u010b"+
		"\u0005)\u0000\u0000\u010b\u0117\u0003$\u0012\u000b\u010c\u010d\n\t\u0000"+
		"\u0000\u010d\u010e\u0007\u0007\u0000\u0000\u010e\u0117\u0003$\u0012\t"+
		"\u010f\u0110\n\u0017\u0000\u0000\u0110\u0117\u0003*\u0015\u0000\u0111"+
		"\u0112\n\u0016\u0000\u0000\u0112\u0117\u0003&\u0013\u0000\u0113\u0114"+
		"\n\u0015\u0000\u0000\u0114\u0115\u00055\u0000\u0000\u0115\u0117\u0005"+
		"8\u0000\u0000\u0116\u00fa\u0001\u0000\u0000\u0000\u0116\u00fd\u0001\u0000"+
		"\u0000\u0000\u0116\u0100\u0001\u0000\u0000\u0000\u0116\u0103\u0001\u0000"+
		"\u0000\u0000\u0116\u0106\u0001\u0000\u0000\u0000\u0116\u0109\u0001\u0000"+
		"\u0000\u0000\u0116\u010c\u0001\u0000\u0000\u0000\u0116\u010f\u0001\u0000"+
		"\u0000\u0000\u0116\u0111\u0001\u0000\u0000\u0000\u0116\u0113\u0001\u0000"+
		"\u0000\u0000\u0117\u011a\u0001\u0000\u0000\u0000\u0118\u0116\u0001\u0000"+
		"\u0000\u0000\u0118\u0119\u0001\u0000\u0000\u0000\u0119%\u0001\u0000\u0000"+
		"\u0000\u011a\u0118\u0001\u0000\u0000\u0000\u011b\u011d\u0005\u001d\u0000"+
		"\u0000\u011c\u011e\u0003(\u0014\u0000\u011d\u011c\u0001\u0000\u0000\u0000"+
		"\u011d\u011e\u0001\u0000\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000"+
		"\u011f\u0120\u0005\u001e\u0000\u0000\u0120\'\u0001\u0000\u0000\u0000\u0121"+
		"\u0126\u0003$\u0012\u0000\u0122\u0123\u00056\u0000\u0000\u0123\u0125\u0003"+
		"$\u0012\u0000\u0124\u0122\u0001\u0000\u0000\u0000\u0125\u0128\u0001\u0000"+
		"\u0000\u0000\u0126\u0124\u0001\u0000\u0000\u0000\u0126\u0127\u0001\u0000"+
		"\u0000\u0000\u0127)\u0001\u0000\u0000\u0000\u0128\u0126\u0001\u0000\u0000"+
		"\u0000\u0129\u012a\u0005\u001f\u0000\u0000\u012a\u012b\u0003$\u0012\u0000"+
		"\u012b\u012c\u0005 \u0000\u0000\u012c+\u0001\u0000\u0000\u0000\u0017/"+
		">JRU[fpy\u0087\u0092\u0098\u009f\u00b9\u00bf\u00c8\u00d0\u00db\u00f8\u0116"+
		"\u0118\u011d\u0126";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}