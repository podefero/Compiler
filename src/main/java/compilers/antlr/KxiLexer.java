// Generated from /home/nag/Documents/UVU/Spring2024/Compiler/CompilerJava/src/main/java/compilers/antlr/KxiLexer.g4 by ANTLR 4.13.1
package compilers.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class KxiLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BOOL=1, BREAK=2, CASE=3, CLASS=4, CHAR_KEY=5, CIN=6, COUT=7, DEFAULT=8, 
		ELSE=9, FALSE=10, FOR=11, IF=12, INT=13, NEW=14, NULL=15, PUBLIC=16, PRIVATE=17, 
		RETURN=18, STATIC=19, STRING=20, SWITCH=21, THIS=22, TRUE=23, VOID=24, 
		WHILE=25, SEMICOLON=26, LCURLY=27, RCURLY=28, LPARENTH=29, RPARENTH=30, 
		LBRACKET=31, RBRACKET=32, EQUALS=33, EQUALSEQUALS=34, NOTEQUALS=35, GREATEREQUALS=36, 
		LESSEQUALS=37, LESSTHEN=38, GREATERTHEN=39, AND=40, OR=41, NOT=42, PLUS=43, 
		SUBTRACT=44, MULT=45, DIVIDE=46, PLUSEQUALS=47, SUBEQUALS=48, MULTEQUALS=49, 
		DIVEQUALS=50, OUTSTREAM=51, INSTREAM=52, DOT=53, COMMA=54, COLON=55, IDENTIFIER=56, 
		CHARLIT=57, STRINGLIT=58, INTLIT=59, COMMENT=60, MULTI_COMMENT=61, WS=62, 
		NEWLINE=63, UNKNOWN=64;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public String printTokens() {
		CommonTokenStream tokens = new CommonTokenStream(this);
		tokens.fill();
		List<Token> tokenList = tokens.getTokens();
		StringBuilder stringBuilder = new StringBuilder();
		for (Token token : tokenList) {
			String tokenName = getVocabulary().getSymbolicName(token.getType());
			stringBuilder.append("Line#: " + token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText() + " " + tokenName + "\n");
		}
		return stringBuilder.toString();
	}

	private static String[] makeRuleNames() {
		return new String[] {
			"ALPHA", "DIGIT", "UNDER_SCORE", "LINE_ENDING", "UNESCAPED_CHAR", "ESCAPED_CHAR", 
			"CHAR", "B", "O", "L", "T", "R", "U", "E", "F", "A", "S", "K", "C", "H", 
			"I", "N", "D", "W", "P", "V", "G", "M", "BOOL", "BREAK", "CASE", "CLASS", 
			"CHAR_KEY", "CIN", "COUT", "DEFAULT", "ELSE", "FALSE", "FOR", "IF", "INT", 
			"NEW", "NULL", "PUBLIC", "PRIVATE", "RETURN", "STATIC", "STRING", "SWITCH", 
			"THIS", "TRUE", "VOID", "WHILE", "SEMICOLON", "LCURLY", "RCURLY", "LPARENTH", 
			"RPARENTH", "LBRACKET", "RBRACKET", "EQUALS", "EQUALSEQUALS", "NOTEQUALS", 
			"GREATEREQUALS", "LESSEQUALS", "LESSTHEN", "GREATERTHEN", "AND", "OR", 
			"NOT", "PLUS", "SUBTRACT", "MULT", "DIVIDE", "PLUSEQUALS", "SUBEQUALS", 
			"MULTEQUALS", "DIVEQUALS", "OUTSTREAM", "INSTREAM", "DOT", "COMMA", "COLON", 
			"IDENTIFIER", "CHARLIT", "STRINGLIT", "INTLIT", "COMMENT", "MULTI_COMMENT", 
			"WS", "NEWLINE", "UNKNOWN"
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
			null, "BOOL", "BREAK", "CASE", "CLASS", "CHAR_KEY", "CIN", "COUT", "DEFAULT", 
			"ELSE", "FALSE", "FOR", "IF", "INT", "NEW", "NULL", "PUBLIC", "PRIVATE", 
			"RETURN", "STATIC", "STRING", "SWITCH", "THIS", "TRUE", "VOID", "WHILE", 
			"SEMICOLON", "LCURLY", "RCURLY", "LPARENTH", "RPARENTH", "LBRACKET", 
			"RBRACKET", "EQUALS", "EQUALSEQUALS", "NOTEQUALS", "GREATEREQUALS", "LESSEQUALS", 
			"LESSTHEN", "GREATERTHEN", "AND", "OR", "NOT", "PLUS", "SUBTRACT", "MULT", 
			"DIVIDE", "PLUSEQUALS", "SUBEQUALS", "MULTEQUALS", "DIVEQUALS", "OUTSTREAM", 
			"INSTREAM", "DOT", "COMMA", "COLON", "IDENTIFIER", "CHARLIT", "STRINGLIT", 
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


	public KxiLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "KxiLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000@\u0216\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007"+
		"0\u00021\u00071\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u0007"+
		"5\u00026\u00076\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007"+
		":\u0002;\u0007;\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007"+
		"?\u0002@\u0007@\u0002A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007"+
		"D\u0002E\u0007E\u0002F\u0007F\u0002G\u0007G\u0002H\u0007H\u0002I\u0007"+
		"I\u0002J\u0007J\u0002K\u0007K\u0002L\u0007L\u0002M\u0007M\u0002N\u0007"+
		"N\u0002O\u0007O\u0002P\u0007P\u0002Q\u0007Q\u0002R\u0007R\u0002S\u0007"+
		"S\u0002T\u0007T\u0002U\u0007U\u0002V\u0007V\u0002W\u0007W\u0002X\u0007"+
		"X\u0002Y\u0007Y\u0002Z\u0007Z\u0002[\u0007[\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0003\u0003\u00c3\b\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0003\u0006\u00cc\b\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001 \u0001 \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0001"+
		"\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0001#\u0001#"+
		"\u0001#\u0001#\u0001#\u0001$\u0001$\u0001$\u0001$\u0001$\u0001%\u0001"+
		"%\u0001%\u0001%\u0001%\u0001%\u0001&\u0001&\u0001&\u0001&\u0001\'\u0001"+
		"\'\u0001\'\u0001(\u0001(\u0001(\u0001(\u0001)\u0001)\u0001)\u0001)\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001+\u0001+\u0001+\u0001+\u0001+\u0001"+
		"+\u0001+\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001.\u0001.\u0001.\u0001"+
		".\u0001.\u0001.\u0001.\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001"+
		"/\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u00011\u00011\u0001"+
		"1\u00011\u00011\u00012\u00012\u00012\u00012\u00012\u00013\u00013\u0001"+
		"3\u00013\u00013\u00014\u00014\u00014\u00014\u00014\u00014\u00015\u0001"+
		"5\u00016\u00016\u00017\u00017\u00018\u00018\u00019\u00019\u0001:\u0001"+
		":\u0001;\u0001;\u0001<\u0001<\u0001=\u0001=\u0001=\u0001>\u0001>\u0001"+
		">\u0001?\u0001?\u0001?\u0001@\u0001@\u0001@\u0001A\u0001A\u0001B\u0001"+
		"B\u0001C\u0001C\u0001C\u0001D\u0001D\u0001D\u0001E\u0001E\u0001F\u0001"+
		"F\u0001G\u0001G\u0001H\u0001H\u0001I\u0001I\u0001J\u0001J\u0001J\u0001"+
		"K\u0001K\u0001K\u0001L\u0001L\u0001L\u0001M\u0001M\u0001M\u0001N\u0001"+
		"N\u0001N\u0001O\u0001O\u0001O\u0001P\u0001P\u0001Q\u0001Q\u0001R\u0001"+
		"R\u0001S\u0001S\u0003S\u01cd\bS\u0001S\u0001S\u0001S\u0005S\u01d2\bS\n"+
		"S\fS\u01d5\tS\u0001T\u0001T\u0001T\u0003T\u01da\bT\u0001T\u0001T\u0001"+
		"U\u0001U\u0001U\u0001U\u0001U\u0005U\u01e3\bU\nU\fU\u01e6\tU\u0001U\u0001"+
		"U\u0001V\u0004V\u01eb\bV\u000bV\fV\u01ec\u0001W\u0001W\u0001W\u0001W\u0005"+
		"W\u01f3\bW\nW\fW\u01f6\tW\u0001W\u0001W\u0001W\u0001W\u0001X\u0001X\u0001"+
		"X\u0001X\u0005X\u0200\bX\nX\fX\u0203\tX\u0001X\u0001X\u0001X\u0001X\u0001"+
		"X\u0001Y\u0004Y\u020b\bY\u000bY\fY\u020c\u0001Y\u0001Y\u0001Z\u0001Z\u0001"+
		"Z\u0001Z\u0001[\u0001[\u0000\u0000\\\u0001\u0000\u0003\u0000\u0005\u0000"+
		"\u0007\u0000\t\u0000\u000b\u0000\r\u0000\u000f\u0000\u0011\u0000\u0013"+
		"\u0000\u0015\u0000\u0017\u0000\u0019\u0000\u001b\u0000\u001d\u0000\u001f"+
		"\u0000!\u0000#\u0000%\u0000\'\u0000)\u0000+\u0000-\u0000/\u00001\u0000"+
		"3\u00005\u00007\u00009\u0001;\u0002=\u0003?\u0004A\u0005C\u0006E\u0007"+
		"G\bI\tK\nM\u000bO\fQ\rS\u000eU\u000fW\u0010Y\u0011[\u0012]\u0013_\u0014"+
		"a\u0015c\u0016e\u0017g\u0018i\u0019k\u001am\u001bo\u001cq\u001ds\u001e"+
		"u\u001fw y!{\"}#\u007f$\u0081%\u0083&\u0085\'\u0087(\u0089)\u008b*\u008d"+
		"+\u008f,\u0091-\u0093.\u0095/\u00970\u00991\u009b2\u009d3\u009f4\u00a1"+
		"5\u00a36\u00a57\u00a78\u00a99\u00ab:\u00ad;\u00af<\u00b1=\u00b3>\u00b5"+
		"?\u00b7@\u0001\u0000\u0007\u0002\u0000AZaz\u0001\u000009\u0002\u0000\n"+
		"\n\r\r\u0003\u0000 !([]~\u0004\u0000\\\\nnrrtt\u0002\u0000\"\"\'\'\u0002"+
		"\u0000\t\t  \u0207\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000"+
		"\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000"+
		"A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001"+
		"\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000"+
		"\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000\u0000\u0000"+
		"O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0000S\u0001"+
		"\u0000\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000W\u0001\u0000\u0000"+
		"\u0000\u0000Y\u0001\u0000\u0000\u0000\u0000[\u0001\u0000\u0000\u0000\u0000"+
		"]\u0001\u0000\u0000\u0000\u0000_\u0001\u0000\u0000\u0000\u0000a\u0001"+
		"\u0000\u0000\u0000\u0000c\u0001\u0000\u0000\u0000\u0000e\u0001\u0000\u0000"+
		"\u0000\u0000g\u0001\u0000\u0000\u0000\u0000i\u0001\u0000\u0000\u0000\u0000"+
		"k\u0001\u0000\u0000\u0000\u0000m\u0001\u0000\u0000\u0000\u0000o\u0001"+
		"\u0000\u0000\u0000\u0000q\u0001\u0000\u0000\u0000\u0000s\u0001\u0000\u0000"+
		"\u0000\u0000u\u0001\u0000\u0000\u0000\u0000w\u0001\u0000\u0000\u0000\u0000"+
		"y\u0001\u0000\u0000\u0000\u0000{\u0001\u0000\u0000\u0000\u0000}\u0001"+
		"\u0000\u0000\u0000\u0000\u007f\u0001\u0000\u0000\u0000\u0000\u0081\u0001"+
		"\u0000\u0000\u0000\u0000\u0083\u0001\u0000\u0000\u0000\u0000\u0085\u0001"+
		"\u0000\u0000\u0000\u0000\u0087\u0001\u0000\u0000\u0000\u0000\u0089\u0001"+
		"\u0000\u0000\u0000\u0000\u008b\u0001\u0000\u0000\u0000\u0000\u008d\u0001"+
		"\u0000\u0000\u0000\u0000\u008f\u0001\u0000\u0000\u0000\u0000\u0091\u0001"+
		"\u0000\u0000\u0000\u0000\u0093\u0001\u0000\u0000\u0000\u0000\u0095\u0001"+
		"\u0000\u0000\u0000\u0000\u0097\u0001\u0000\u0000\u0000\u0000\u0099\u0001"+
		"\u0000\u0000\u0000\u0000\u009b\u0001\u0000\u0000\u0000\u0000\u009d\u0001"+
		"\u0000\u0000\u0000\u0000\u009f\u0001\u0000\u0000\u0000\u0000\u00a1\u0001"+
		"\u0000\u0000\u0000\u0000\u00a3\u0001\u0000\u0000\u0000\u0000\u00a5\u0001"+
		"\u0000\u0000\u0000\u0000\u00a7\u0001\u0000\u0000\u0000\u0000\u00a9\u0001"+
		"\u0000\u0000\u0000\u0000\u00ab\u0001\u0000\u0000\u0000\u0000\u00ad\u0001"+
		"\u0000\u0000\u0000\u0000\u00af\u0001\u0000\u0000\u0000\u0000\u00b1\u0001"+
		"\u0000\u0000\u0000\u0000\u00b3\u0001\u0000\u0000\u0000\u0000\u00b5\u0001"+
		"\u0000\u0000\u0000\u0000\u00b7\u0001\u0000\u0000\u0000\u0001\u00b9\u0001"+
		"\u0000\u0000\u0000\u0003\u00bb\u0001\u0000\u0000\u0000\u0005\u00bd\u0001"+
		"\u0000\u0000\u0000\u0007\u00c2\u0001\u0000\u0000\u0000\t\u00c4\u0001\u0000"+
		"\u0000\u0000\u000b\u00c6\u0001\u0000\u0000\u0000\r\u00cb\u0001\u0000\u0000"+
		"\u0000\u000f\u00cd\u0001\u0000\u0000\u0000\u0011\u00cf\u0001\u0000\u0000"+
		"\u0000\u0013\u00d1\u0001\u0000\u0000\u0000\u0015\u00d3\u0001\u0000\u0000"+
		"\u0000\u0017\u00d5\u0001\u0000\u0000\u0000\u0019\u00d7\u0001\u0000\u0000"+
		"\u0000\u001b\u00d9\u0001\u0000\u0000\u0000\u001d\u00db\u0001\u0000\u0000"+
		"\u0000\u001f\u00dd\u0001\u0000\u0000\u0000!\u00df\u0001\u0000\u0000\u0000"+
		"#\u00e1\u0001\u0000\u0000\u0000%\u00e3\u0001\u0000\u0000\u0000\'\u00e5"+
		"\u0001\u0000\u0000\u0000)\u00e7\u0001\u0000\u0000\u0000+\u00e9\u0001\u0000"+
		"\u0000\u0000-\u00eb\u0001\u0000\u0000\u0000/\u00ed\u0001\u0000\u0000\u0000"+
		"1\u00ef\u0001\u0000\u0000\u00003\u00f1\u0001\u0000\u0000\u00005\u00f3"+
		"\u0001\u0000\u0000\u00007\u00f5\u0001\u0000\u0000\u00009\u00f7\u0001\u0000"+
		"\u0000\u0000;\u00fc\u0001\u0000\u0000\u0000=\u0102\u0001\u0000\u0000\u0000"+
		"?\u0107\u0001\u0000\u0000\u0000A\u010d\u0001\u0000\u0000\u0000C\u0112"+
		"\u0001\u0000\u0000\u0000E\u0116\u0001\u0000\u0000\u0000G\u011b\u0001\u0000"+
		"\u0000\u0000I\u0123\u0001\u0000\u0000\u0000K\u0128\u0001\u0000\u0000\u0000"+
		"M\u012e\u0001\u0000\u0000\u0000O\u0132\u0001\u0000\u0000\u0000Q\u0135"+
		"\u0001\u0000\u0000\u0000S\u0139\u0001\u0000\u0000\u0000U\u013d\u0001\u0000"+
		"\u0000\u0000W\u0142\u0001\u0000\u0000\u0000Y\u0149\u0001\u0000\u0000\u0000"+
		"[\u0151\u0001\u0000\u0000\u0000]\u0158\u0001\u0000\u0000\u0000_\u015f"+
		"\u0001\u0000\u0000\u0000a\u0166\u0001\u0000\u0000\u0000c\u016d\u0001\u0000"+
		"\u0000\u0000e\u0172\u0001\u0000\u0000\u0000g\u0177\u0001\u0000\u0000\u0000"+
		"i\u017c\u0001\u0000\u0000\u0000k\u0182\u0001\u0000\u0000\u0000m\u0184"+
		"\u0001\u0000\u0000\u0000o\u0186\u0001\u0000\u0000\u0000q\u0188\u0001\u0000"+
		"\u0000\u0000s\u018a\u0001\u0000\u0000\u0000u\u018c\u0001\u0000\u0000\u0000"+
		"w\u018e\u0001\u0000\u0000\u0000y\u0190\u0001\u0000\u0000\u0000{\u0192"+
		"\u0001\u0000\u0000\u0000}\u0195\u0001\u0000\u0000\u0000\u007f\u0198\u0001"+
		"\u0000\u0000\u0000\u0081\u019b\u0001\u0000\u0000\u0000\u0083\u019e\u0001"+
		"\u0000\u0000\u0000\u0085\u01a0\u0001\u0000\u0000\u0000\u0087\u01a2\u0001"+
		"\u0000\u0000\u0000\u0089\u01a5\u0001\u0000\u0000\u0000\u008b\u01a8\u0001"+
		"\u0000\u0000\u0000\u008d\u01aa\u0001\u0000\u0000\u0000\u008f\u01ac\u0001"+
		"\u0000\u0000\u0000\u0091\u01ae\u0001\u0000\u0000\u0000\u0093\u01b0\u0001"+
		"\u0000\u0000\u0000\u0095\u01b2\u0001\u0000\u0000\u0000\u0097\u01b5\u0001"+
		"\u0000\u0000\u0000\u0099\u01b8\u0001\u0000\u0000\u0000\u009b\u01bb\u0001"+
		"\u0000\u0000\u0000\u009d\u01be\u0001\u0000\u0000\u0000\u009f\u01c1\u0001"+
		"\u0000\u0000\u0000\u00a1\u01c4\u0001\u0000\u0000\u0000\u00a3\u01c6\u0001"+
		"\u0000\u0000\u0000\u00a5\u01c8\u0001\u0000\u0000\u0000\u00a7\u01cc\u0001"+
		"\u0000\u0000\u0000\u00a9\u01d6\u0001\u0000\u0000\u0000\u00ab\u01dd\u0001"+
		"\u0000\u0000\u0000\u00ad\u01ea\u0001\u0000\u0000\u0000\u00af\u01ee\u0001"+
		"\u0000\u0000\u0000\u00b1\u01fb\u0001\u0000\u0000\u0000\u00b3\u020a\u0001"+
		"\u0000\u0000\u0000\u00b5\u0210\u0001\u0000\u0000\u0000\u00b7\u0214\u0001"+
		"\u0000\u0000\u0000\u00b9\u00ba\u0007\u0000\u0000\u0000\u00ba\u0002\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bc\u0007\u0001\u0000\u0000\u00bc\u0004\u0001"+
		"\u0000\u0000\u0000\u00bd\u00be\u0005_\u0000\u0000\u00be\u0006\u0001\u0000"+
		"\u0000\u0000\u00bf\u00c0\u0005\r\u0000\u0000\u00c0\u00c3\u0005\n\u0000"+
		"\u0000\u00c1\u00c3\u0007\u0002\u0000\u0000\u00c2\u00bf\u0001\u0000\u0000"+
		"\u0000\u00c2\u00c1\u0001\u0000\u0000\u0000\u00c3\b\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c5\u0007\u0003\u0000\u0000\u00c5\n\u0001\u0000\u0000\u0000\u00c6"+
		"\u00c7\u0005\\\u0000\u0000\u00c7\u00c8\u0007\u0004\u0000\u0000\u00c8\f"+
		"\u0001\u0000\u0000\u0000\u00c9\u00cc\u0003\t\u0004\u0000\u00ca\u00cc\u0003"+
		"\u000b\u0005\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000\u00cb\u00ca\u0001"+
		"\u0000\u0000\u0000\u00cc\u000e\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005"+
		"b\u0000\u0000\u00ce\u0010\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005o\u0000"+
		"\u0000\u00d0\u0012\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005l\u0000\u0000"+
		"\u00d2\u0014\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005t\u0000\u0000\u00d4"+
		"\u0016\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005r\u0000\u0000\u00d6\u0018"+
		"\u0001\u0000\u0000\u0000\u00d7\u00d8\u0005u\u0000\u0000\u00d8\u001a\u0001"+
		"\u0000\u0000\u0000\u00d9\u00da\u0005e\u0000\u0000\u00da\u001c\u0001\u0000"+
		"\u0000\u0000\u00db\u00dc\u0005f\u0000\u0000\u00dc\u001e\u0001\u0000\u0000"+
		"\u0000\u00dd\u00de\u0005a\u0000\u0000\u00de \u0001\u0000\u0000\u0000\u00df"+
		"\u00e0\u0005s\u0000\u0000\u00e0\"\u0001\u0000\u0000\u0000\u00e1\u00e2"+
		"\u0005k\u0000\u0000\u00e2$\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005c"+
		"\u0000\u0000\u00e4&\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005h\u0000\u0000"+
		"\u00e6(\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005i\u0000\u0000\u00e8*"+
		"\u0001\u0000\u0000\u0000\u00e9\u00ea\u0005n\u0000\u0000\u00ea,\u0001\u0000"+
		"\u0000\u0000\u00eb\u00ec\u0005d\u0000\u0000\u00ec.\u0001\u0000\u0000\u0000"+
		"\u00ed\u00ee\u0005w\u0000\u0000\u00ee0\u0001\u0000\u0000\u0000\u00ef\u00f0"+
		"\u0005p\u0000\u0000\u00f02\u0001\u0000\u0000\u0000\u00f1\u00f2\u0005v"+
		"\u0000\u0000\u00f24\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005g\u0000\u0000"+
		"\u00f46\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005m\u0000\u0000\u00f68"+
		"\u0001\u0000\u0000\u0000\u00f7\u00f8\u0003\u000f\u0007\u0000\u00f8\u00f9"+
		"\u0003\u0011\b\u0000\u00f9\u00fa\u0003\u0011\b\u0000\u00fa\u00fb\u0003"+
		"\u0013\t\u0000\u00fb:\u0001\u0000\u0000\u0000\u00fc\u00fd\u0003\u000f"+
		"\u0007\u0000\u00fd\u00fe\u0003\u0017\u000b\u0000\u00fe\u00ff\u0003\u001b"+
		"\r\u0000\u00ff\u0100\u0003\u001f\u000f\u0000\u0100\u0101\u0003#\u0011"+
		"\u0000\u0101<\u0001\u0000\u0000\u0000\u0102\u0103\u0003%\u0012\u0000\u0103"+
		"\u0104\u0003\u001f\u000f\u0000\u0104\u0105\u0003!\u0010\u0000\u0105\u0106"+
		"\u0003\u001b\r\u0000\u0106>\u0001\u0000\u0000\u0000\u0107\u0108\u0003"+
		"%\u0012\u0000\u0108\u0109\u0003\u0013\t\u0000\u0109\u010a\u0003\u001f"+
		"\u000f\u0000\u010a\u010b\u0003!\u0010\u0000\u010b\u010c\u0003!\u0010\u0000"+
		"\u010c@\u0001\u0000\u0000\u0000\u010d\u010e\u0003%\u0012\u0000\u010e\u010f"+
		"\u0003\'\u0013\u0000\u010f\u0110\u0003\u001f\u000f\u0000\u0110\u0111\u0003"+
		"\u0017\u000b\u0000\u0111B\u0001\u0000\u0000\u0000\u0112\u0113\u0003%\u0012"+
		"\u0000\u0113\u0114\u0003)\u0014\u0000\u0114\u0115\u0003+\u0015\u0000\u0115"+
		"D\u0001\u0000\u0000\u0000\u0116\u0117\u0003%\u0012\u0000\u0117\u0118\u0003"+
		"\u0011\b\u0000\u0118\u0119\u0003\u0019\f\u0000\u0119\u011a\u0003\u0015"+
		"\n\u0000\u011aF\u0001\u0000\u0000\u0000\u011b\u011c\u0003-\u0016\u0000"+
		"\u011c\u011d\u0003\u001b\r\u0000\u011d\u011e\u0003\u001d\u000e\u0000\u011e"+
		"\u011f\u0003\u001f\u000f\u0000\u011f\u0120\u0003\u0019\f\u0000\u0120\u0121"+
		"\u0003\u0013\t\u0000\u0121\u0122\u0003\u0015\n\u0000\u0122H\u0001\u0000"+
		"\u0000\u0000\u0123\u0124\u0003\u001b\r\u0000\u0124\u0125\u0003\u0013\t"+
		"\u0000\u0125\u0126\u0003!\u0010\u0000\u0126\u0127\u0003\u001b\r\u0000"+
		"\u0127J\u0001\u0000\u0000\u0000\u0128\u0129\u0003\u001d\u000e\u0000\u0129"+
		"\u012a\u0003\u001f\u000f\u0000\u012a\u012b\u0003\u0013\t\u0000\u012b\u012c"+
		"\u0003!\u0010\u0000\u012c\u012d\u0003\u001b\r\u0000\u012dL\u0001\u0000"+
		"\u0000\u0000\u012e\u012f\u0003\u001d\u000e\u0000\u012f\u0130\u0003\u0011"+
		"\b\u0000\u0130\u0131\u0003\u0017\u000b\u0000\u0131N\u0001\u0000\u0000"+
		"\u0000\u0132\u0133\u0003)\u0014\u0000\u0133\u0134\u0003\u001d\u000e\u0000"+
		"\u0134P\u0001\u0000\u0000\u0000\u0135\u0136\u0003)\u0014\u0000\u0136\u0137"+
		"\u0003+\u0015\u0000\u0137\u0138\u0003\u0015\n\u0000\u0138R\u0001\u0000"+
		"\u0000\u0000\u0139\u013a\u0003+\u0015\u0000\u013a\u013b\u0003\u001b\r"+
		"\u0000\u013b\u013c\u0003/\u0017\u0000\u013cT\u0001\u0000\u0000\u0000\u013d"+
		"\u013e\u0003+\u0015\u0000\u013e\u013f\u0003\u0019\f\u0000\u013f\u0140"+
		"\u0003\u0013\t\u0000\u0140\u0141\u0003\u0013\t\u0000\u0141V\u0001\u0000"+
		"\u0000\u0000\u0142\u0143\u00031\u0018\u0000\u0143\u0144\u0003\u0019\f"+
		"\u0000\u0144\u0145\u0003\u000f\u0007\u0000\u0145\u0146\u0003\u0013\t\u0000"+
		"\u0146\u0147\u0003)\u0014\u0000\u0147\u0148\u0003%\u0012\u0000\u0148X"+
		"\u0001\u0000\u0000\u0000\u0149\u014a\u00031\u0018\u0000\u014a\u014b\u0003"+
		"\u0017\u000b\u0000\u014b\u014c\u0003)\u0014\u0000\u014c\u014d\u00033\u0019"+
		"\u0000\u014d\u014e\u0003\u001f\u000f\u0000\u014e\u014f\u0003\u0015\n\u0000"+
		"\u014f\u0150\u0003\u001b\r\u0000\u0150Z\u0001\u0000\u0000\u0000\u0151"+
		"\u0152\u0003\u0017\u000b\u0000\u0152\u0153\u0003\u001b\r\u0000\u0153\u0154"+
		"\u0003\u0015\n\u0000\u0154\u0155\u0003\u0019\f\u0000\u0155\u0156\u0003"+
		"\u0017\u000b\u0000\u0156\u0157\u0003+\u0015\u0000\u0157\\\u0001\u0000"+
		"\u0000\u0000\u0158\u0159\u0003!\u0010\u0000\u0159\u015a\u0003\u0015\n"+
		"\u0000\u015a\u015b\u0003\u001f\u000f\u0000\u015b\u015c\u0003\u0015\n\u0000"+
		"\u015c\u015d\u0003)\u0014\u0000\u015d\u015e\u0003%\u0012\u0000\u015e^"+
		"\u0001\u0000\u0000\u0000\u015f\u0160\u0003!\u0010\u0000\u0160\u0161\u0003"+
		"\u0015\n\u0000\u0161\u0162\u0003\u0017\u000b\u0000\u0162\u0163\u0003)"+
		"\u0014\u0000\u0163\u0164\u0003+\u0015\u0000\u0164\u0165\u00035\u001a\u0000"+
		"\u0165`\u0001\u0000\u0000\u0000\u0166\u0167\u0003!\u0010\u0000\u0167\u0168"+
		"\u0003/\u0017\u0000\u0168\u0169\u0003)\u0014\u0000\u0169\u016a\u0003\u0015"+
		"\n\u0000\u016a\u016b\u0003%\u0012\u0000\u016b\u016c\u0003\'\u0013\u0000"+
		"\u016cb\u0001\u0000\u0000\u0000\u016d\u016e\u0003\u0015\n\u0000\u016e"+
		"\u016f\u0003\'\u0013\u0000\u016f\u0170\u0003)\u0014\u0000\u0170\u0171"+
		"\u0003!\u0010\u0000\u0171d\u0001\u0000\u0000\u0000\u0172\u0173\u0003\u0015"+
		"\n\u0000\u0173\u0174\u0003\u0017\u000b\u0000\u0174\u0175\u0003\u0019\f"+
		"\u0000\u0175\u0176\u0003\u001b\r\u0000\u0176f\u0001\u0000\u0000\u0000"+
		"\u0177\u0178\u00033\u0019\u0000\u0178\u0179\u0003\u0011\b\u0000\u0179"+
		"\u017a\u0003)\u0014\u0000\u017a\u017b\u0003-\u0016\u0000\u017bh\u0001"+
		"\u0000\u0000\u0000\u017c\u017d\u0003/\u0017\u0000\u017d\u017e\u0003\'"+
		"\u0013\u0000\u017e\u017f\u0003)\u0014\u0000\u017f\u0180\u0003\u0013\t"+
		"\u0000\u0180\u0181\u0003\u001b\r\u0000\u0181j\u0001\u0000\u0000\u0000"+
		"\u0182\u0183\u0005;\u0000\u0000\u0183l\u0001\u0000\u0000\u0000\u0184\u0185"+
		"\u0005{\u0000\u0000\u0185n\u0001\u0000\u0000\u0000\u0186\u0187\u0005}"+
		"\u0000\u0000\u0187p\u0001\u0000\u0000\u0000\u0188\u0189\u0005(\u0000\u0000"+
		"\u0189r\u0001\u0000\u0000\u0000\u018a\u018b\u0005)\u0000\u0000\u018bt"+
		"\u0001\u0000\u0000\u0000\u018c\u018d\u0005[\u0000\u0000\u018dv\u0001\u0000"+
		"\u0000\u0000\u018e\u018f\u0005]\u0000\u0000\u018fx\u0001\u0000\u0000\u0000"+
		"\u0190\u0191\u0005=\u0000\u0000\u0191z\u0001\u0000\u0000\u0000\u0192\u0193"+
		"\u0005=\u0000\u0000\u0193\u0194\u0005=\u0000\u0000\u0194|\u0001\u0000"+
		"\u0000\u0000\u0195\u0196\u0005!\u0000\u0000\u0196\u0197\u0005=\u0000\u0000"+
		"\u0197~\u0001\u0000\u0000\u0000\u0198\u0199\u0005>\u0000\u0000\u0199\u019a"+
		"\u0005=\u0000\u0000\u019a\u0080\u0001\u0000\u0000\u0000\u019b\u019c\u0005"+
		"<\u0000\u0000\u019c\u019d\u0005=\u0000\u0000\u019d\u0082\u0001\u0000\u0000"+
		"\u0000\u019e\u019f\u0005<\u0000\u0000\u019f\u0084\u0001\u0000\u0000\u0000"+
		"\u01a0\u01a1\u0005>\u0000\u0000\u01a1\u0086\u0001\u0000\u0000\u0000\u01a2"+
		"\u01a3\u0005&\u0000\u0000\u01a3\u01a4\u0005&\u0000\u0000\u01a4\u0088\u0001"+
		"\u0000\u0000\u0000\u01a5\u01a6\u0005|\u0000\u0000\u01a6\u01a7\u0005|\u0000"+
		"\u0000\u01a7\u008a\u0001\u0000\u0000\u0000\u01a8\u01a9\u0005!\u0000\u0000"+
		"\u01a9\u008c\u0001\u0000\u0000\u0000\u01aa\u01ab\u0005+\u0000\u0000\u01ab"+
		"\u008e\u0001\u0000\u0000\u0000\u01ac\u01ad\u0005-\u0000\u0000\u01ad\u0090"+
		"\u0001\u0000\u0000\u0000\u01ae\u01af\u0005*\u0000\u0000\u01af\u0092\u0001"+
		"\u0000\u0000\u0000\u01b0\u01b1\u0005/\u0000\u0000\u01b1\u0094\u0001\u0000"+
		"\u0000\u0000\u01b2\u01b3\u0005+\u0000\u0000\u01b3\u01b4\u0005=\u0000\u0000"+
		"\u01b4\u0096\u0001\u0000\u0000\u0000\u01b5\u01b6\u0005-\u0000\u0000\u01b6"+
		"\u01b7\u0005=\u0000\u0000\u01b7\u0098\u0001\u0000\u0000\u0000\u01b8\u01b9"+
		"\u0005*\u0000\u0000\u01b9\u01ba\u0005=\u0000\u0000\u01ba\u009a\u0001\u0000"+
		"\u0000\u0000\u01bb\u01bc\u0005/\u0000\u0000\u01bc\u01bd\u0005=\u0000\u0000"+
		"\u01bd\u009c\u0001\u0000\u0000\u0000\u01be\u01bf\u0005<\u0000\u0000\u01bf"+
		"\u01c0\u0005<\u0000\u0000\u01c0\u009e\u0001\u0000\u0000\u0000\u01c1\u01c2"+
		"\u0005>\u0000\u0000\u01c2\u01c3\u0005>\u0000\u0000\u01c3\u00a0\u0001\u0000"+
		"\u0000\u0000\u01c4\u01c5\u0005.\u0000\u0000\u01c5\u00a2\u0001\u0000\u0000"+
		"\u0000\u01c6\u01c7\u0005,\u0000\u0000\u01c7\u00a4\u0001\u0000\u0000\u0000"+
		"\u01c8\u01c9\u0005:\u0000\u0000\u01c9\u00a6\u0001\u0000\u0000\u0000\u01ca"+
		"\u01cd\u0003\u0001\u0000\u0000\u01cb\u01cd\u0003\u0005\u0002\u0000\u01cc"+
		"\u01ca\u0001\u0000\u0000\u0000\u01cc\u01cb\u0001\u0000\u0000\u0000\u01cd"+
		"\u01d3\u0001\u0000\u0000\u0000\u01ce\u01d2\u0003\u0001\u0000\u0000\u01cf"+
		"\u01d2\u0003\u0003\u0001\u0000\u01d0\u01d2\u0003\u0005\u0002\u0000\u01d1"+
		"\u01ce\u0001\u0000\u0000\u0000\u01d1\u01cf\u0001\u0000\u0000\u0000\u01d1"+
		"\u01d0\u0001\u0000\u0000\u0000\u01d2\u01d5\u0001\u0000\u0000\u0000\u01d3"+
		"\u01d1\u0001\u0000\u0000\u0000\u01d3\u01d4\u0001\u0000\u0000\u0000\u01d4"+
		"\u00a8\u0001\u0000\u0000\u0000\u01d5\u01d3\u0001\u0000\u0000\u0000\u01d6"+
		"\u01d9\u0005\'\u0000\u0000\u01d7\u01da\u0003\r\u0006\u0000\u01d8\u01da"+
		"\u0007\u0005\u0000\u0000\u01d9\u01d7\u0001\u0000\u0000\u0000\u01d9\u01d8"+
		"\u0001\u0000\u0000\u0000\u01da\u01db\u0001\u0000\u0000\u0000\u01db\u01dc"+
		"\u0005\'\u0000\u0000\u01dc\u00aa\u0001\u0000\u0000\u0000\u01dd\u01e4\u0005"+
		"\"\u0000\u0000\u01de\u01e3\u0003\r\u0006\u0000\u01df\u01e3\u0005\'\u0000"+
		"\u0000\u01e0\u01e1\u0005\\\u0000\u0000\u01e1\u01e3\u0005\"\u0000\u0000"+
		"\u01e2\u01de\u0001\u0000\u0000\u0000\u01e2\u01df\u0001\u0000\u0000\u0000"+
		"\u01e2\u01e0\u0001\u0000\u0000\u0000\u01e3\u01e6\u0001\u0000\u0000\u0000"+
		"\u01e4\u01e2\u0001\u0000\u0000\u0000\u01e4\u01e5\u0001\u0000\u0000\u0000"+
		"\u01e5\u01e7\u0001\u0000\u0000\u0000\u01e6\u01e4\u0001\u0000\u0000\u0000"+
		"\u01e7\u01e8\u0005\"\u0000\u0000\u01e8\u00ac\u0001\u0000\u0000\u0000\u01e9"+
		"\u01eb\u0003\u0003\u0001\u0000\u01ea\u01e9\u0001\u0000\u0000\u0000\u01eb"+
		"\u01ec\u0001\u0000\u0000\u0000\u01ec\u01ea\u0001\u0000\u0000\u0000\u01ec"+
		"\u01ed\u0001\u0000\u0000\u0000\u01ed\u00ae\u0001\u0000\u0000\u0000\u01ee"+
		"\u01ef\u0005/\u0000\u0000\u01ef\u01f0\u0005/\u0000\u0000\u01f0\u01f4\u0001"+
		"\u0000\u0000\u0000\u01f1\u01f3\b\u0002\u0000\u0000\u01f2\u01f1\u0001\u0000"+
		"\u0000\u0000\u01f3\u01f6\u0001\u0000\u0000\u0000\u01f4\u01f2\u0001\u0000"+
		"\u0000\u0000\u01f4\u01f5\u0001\u0000\u0000\u0000\u01f5\u01f7\u0001\u0000"+
		"\u0000\u0000\u01f6\u01f4\u0001\u0000\u0000\u0000\u01f7\u01f8\u0003\u0007"+
		"\u0003\u0000\u01f8\u01f9\u0001\u0000\u0000\u0000\u01f9\u01fa\u0006W\u0000"+
		"\u0000\u01fa\u00b0\u0001\u0000\u0000\u0000\u01fb\u01fc\u0005/\u0000\u0000"+
		"\u01fc\u01fd\u0005*\u0000\u0000\u01fd\u0201\u0001\u0000\u0000\u0000\u01fe"+
		"\u0200\t\u0000\u0000\u0000\u01ff\u01fe\u0001\u0000\u0000\u0000\u0200\u0203"+
		"\u0001\u0000\u0000\u0000\u0201\u01ff\u0001\u0000\u0000\u0000\u0201\u0202"+
		"\u0001\u0000\u0000\u0000\u0202\u0204\u0001\u0000\u0000\u0000\u0203\u0201"+
		"\u0001\u0000\u0000\u0000\u0204\u0205\u0005*\u0000\u0000\u0205\u0206\u0005"+
		"/\u0000\u0000\u0206\u0207\u0001\u0000\u0000\u0000\u0207\u0208\u0006X\u0000"+
		"\u0000\u0208\u00b2\u0001\u0000\u0000\u0000\u0209\u020b\u0007\u0006\u0000"+
		"\u0000\u020a\u0209\u0001\u0000\u0000\u0000\u020b\u020c\u0001\u0000\u0000"+
		"\u0000\u020c\u020a\u0001\u0000\u0000\u0000\u020c\u020d\u0001\u0000\u0000"+
		"\u0000\u020d\u020e\u0001\u0000\u0000\u0000\u020e\u020f\u0006Y\u0000\u0000"+
		"\u020f\u00b4\u0001\u0000\u0000\u0000\u0210\u0211\u0003\u0007\u0003\u0000"+
		"\u0211\u0212\u0001\u0000\u0000\u0000\u0212\u0213\u0006Z\u0000\u0000\u0213"+
		"\u00b6\u0001\u0000\u0000\u0000\u0214\u0215\t\u0000\u0000\u0000\u0215\u00b8"+
		"\u0001\u0000\u0000\u0000\r\u0000\u00c2\u00cb\u01cc\u01d1\u01d3\u01d9\u01e2"+
		"\u01e4\u01ec\u01f4\u0201\u020c\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}