// Generated from While.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WhileLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		While=1, Then=2, Do=3, If=4, Else=5, Write=6, WriteLn=7, Read=8, Skip=9, 
		True=10, False=11, Return=12, Array=13, Function=14, Integer=15, Float=16, 
		Identifier=17, Assign=18, Plus=19, Minus=20, Multiply=21, Divide=22, Equals=23, 
		NotEquals=24, LessEqualThan=25, LessThan=26, GreaterThan=27, GreaterEqualThan=28, 
		Comma=29, BooleanAnd=30, BooleanOr=31, BooleanNot=32, BooleanXor=33, Semicolon=34, 
		OpenParen=35, CloseParen=36, OpenSquareBracket=37, CloseSquareBracket=38, 
		WhiteSpace=39, String=40, Comment=41;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"While", "Then", "Do", "If", "Else", "Write", "WriteLn", "Read", "Skip", 
		"True", "False", "Return", "Array", "Function", "Digit", "Integer", "Float", 
		"Letters", "Identifier", "Assign", "Plus", "Minus", "Multiply", "Divide", 
		"Equals", "NotEquals", "LessEqualThan", "LessThan", "GreaterThan", "GreaterEqualThan", 
		"Comma", "BooleanAnd", "BooleanOr", "BooleanNot", "BooleanXor", "Semicolon", 
		"OpenParen", "CloseParen", "OpenSquareBracket", "CloseSquareBracket", 
		"WhiteSpace", "String", "Comment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'while'", "'then'", "'do'", "'if'", "'else'", "'write'", "'writeln'", 
		"'read'", "'skip'", "'true'", "'false'", "'return'", "'array'", "'function'", 
		null, null, null, "':='", "'+'", "'-'", "'*'", "'/'", "'='", "'!='", "'<='", 
		"'<'", "'>'", "'>='", "','", "'&'", "'|'", "'!'", "'^'", "';'", "'('", 
		"')'", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "While", "Then", "Do", "If", "Else", "Write", "WriteLn", "Read", 
		"Skip", "True", "False", "Return", "Array", "Function", "Integer", "Float", 
		"Identifier", "Assign", "Plus", "Minus", "Multiply", "Divide", "Equals", 
		"NotEquals", "LessEqualThan", "LessThan", "GreaterThan", "GreaterEqualThan", 
		"Comma", "BooleanAnd", "BooleanOr", "BooleanNot", "BooleanXor", "Semicolon", 
		"OpenParen", "CloseParen", "OpenSquareBracket", "CloseSquareBracket", 
		"WhiteSpace", "String", "Comment"
	};
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


	public WhileLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "While.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2+\u010c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\21\6\21\u00ac\n\21\r\21\16\21\u00ad\3\22\3\22\3\22\7\22\u00b3\n\22"+
		"\f\22\16\22\u00b6\13\22\3\23\3\23\3\24\3\24\3\24\7\24\u00bd\n\24\f\24"+
		"\16\24\u00c0\13\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3"+
		"\31\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)"+
		"\3)\3*\6*\u00f1\n*\r*\16*\u00f2\3*\3*\3+\3+\3+\3+\7+\u00fb\n+\f+\16+\u00fe"+
		"\13+\3+\3+\3,\3,\7,\u0104\n,\f,\16,\u0107\13,\3,\3,\3,\3,\2\2-\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\2!\21"+
		"#\22%\2\'\23)\24+\25-\26/\27\61\30\63\31\65\32\67\339\34;\35=\36?\37A"+
		" C!E\"G#I$K%M&O\'Q(S)U*W+\3\2\6\4\2C\\c|\5\2\13\f\17\17\"\"\3\2))\3\2"+
		"\177\177\u0111\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2"+
		"W\3\2\2\2\3Y\3\2\2\2\5_\3\2\2\2\7d\3\2\2\2\tg\3\2\2\2\13j\3\2\2\2\ro\3"+
		"\2\2\2\17u\3\2\2\2\21}\3\2\2\2\23\u0082\3\2\2\2\25\u0087\3\2\2\2\27\u008c"+
		"\3\2\2\2\31\u0092\3\2\2\2\33\u0099\3\2\2\2\35\u009f\3\2\2\2\37\u00a8\3"+
		"\2\2\2!\u00ab\3\2\2\2#\u00af\3\2\2\2%\u00b7\3\2\2\2\'\u00b9\3\2\2\2)\u00c1"+
		"\3\2\2\2+\u00c4\3\2\2\2-\u00c6\3\2\2\2/\u00c8\3\2\2\2\61\u00ca\3\2\2\2"+
		"\63\u00cc\3\2\2\2\65\u00ce\3\2\2\2\67\u00d1\3\2\2\29\u00d4\3\2\2\2;\u00d6"+
		"\3\2\2\2=\u00d8\3\2\2\2?\u00db\3\2\2\2A\u00dd\3\2\2\2C\u00df\3\2\2\2E"+
		"\u00e1\3\2\2\2G\u00e3\3\2\2\2I\u00e5\3\2\2\2K\u00e7\3\2\2\2M\u00e9\3\2"+
		"\2\2O\u00eb\3\2\2\2Q\u00ed\3\2\2\2S\u00f0\3\2\2\2U\u00f6\3\2\2\2W\u0101"+
		"\3\2\2\2YZ\7y\2\2Z[\7j\2\2[\\\7k\2\2\\]\7n\2\2]^\7g\2\2^\4\3\2\2\2_`\7"+
		"v\2\2`a\7j\2\2ab\7g\2\2bc\7p\2\2c\6\3\2\2\2de\7f\2\2ef\7q\2\2f\b\3\2\2"+
		"\2gh\7k\2\2hi\7h\2\2i\n\3\2\2\2jk\7g\2\2kl\7n\2\2lm\7u\2\2mn\7g\2\2n\f"+
		"\3\2\2\2op\7y\2\2pq\7t\2\2qr\7k\2\2rs\7v\2\2st\7g\2\2t\16\3\2\2\2uv\7"+
		"y\2\2vw\7t\2\2wx\7k\2\2xy\7v\2\2yz\7g\2\2z{\7n\2\2{|\7p\2\2|\20\3\2\2"+
		"\2}~\7t\2\2~\177\7g\2\2\177\u0080\7c\2\2\u0080\u0081\7f\2\2\u0081\22\3"+
		"\2\2\2\u0082\u0083\7u\2\2\u0083\u0084\7m\2\2\u0084\u0085\7k\2\2\u0085"+
		"\u0086\7r\2\2\u0086\24\3\2\2\2\u0087\u0088\7v\2\2\u0088\u0089\7t\2\2\u0089"+
		"\u008a\7w\2\2\u008a\u008b\7g\2\2\u008b\26\3\2\2\2\u008c\u008d\7h\2\2\u008d"+
		"\u008e\7c\2\2\u008e\u008f\7n\2\2\u008f\u0090\7u\2\2\u0090\u0091\7g\2\2"+
		"\u0091\30\3\2\2\2\u0092\u0093\7t\2\2\u0093\u0094\7g\2\2\u0094\u0095\7"+
		"v\2\2\u0095\u0096\7w\2\2\u0096\u0097\7t\2\2\u0097\u0098\7p\2\2\u0098\32"+
		"\3\2\2\2\u0099\u009a\7c\2\2\u009a\u009b\7t\2\2\u009b\u009c\7t\2\2\u009c"+
		"\u009d\7c\2\2\u009d\u009e\7{\2\2\u009e\34\3\2\2\2\u009f\u00a0\7h\2\2\u00a0"+
		"\u00a1\7w\2\2\u00a1\u00a2\7p\2\2\u00a2\u00a3\7e\2\2\u00a3\u00a4\7v\2\2"+
		"\u00a4\u00a5\7k\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7\7p\2\2\u00a7\36\3\2"+
		"\2\2\u00a8\u00a9\4\62;\2\u00a9 \3\2\2\2\u00aa\u00ac\5\37\20\2\u00ab\u00aa"+
		"\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\"\3\2\2\2\u00af\u00b0\5\37\20\2\u00b0\u00b4\7\60\2\2\u00b1\u00b3\5\37"+
		"\20\2\u00b2\u00b1\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4"+
		"\u00b5\3\2\2\2\u00b5$\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8\t\2\2\2"+
		"\u00b8&\3\2\2\2\u00b9\u00be\5%\23\2\u00ba\u00bd\5%\23\2\u00bb\u00bd\5"+
		"\37\20\2\u00bc\u00ba\3\2\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be"+
		"\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf(\3\2\2\2\u00c0\u00be\3\2\2\2"+
		"\u00c1\u00c2\7<\2\2\u00c2\u00c3\7?\2\2\u00c3*\3\2\2\2\u00c4\u00c5\7-\2"+
		"\2\u00c5,\3\2\2\2\u00c6\u00c7\7/\2\2\u00c7.\3\2\2\2\u00c8\u00c9\7,\2\2"+
		"\u00c9\60\3\2\2\2\u00ca\u00cb\7\61\2\2\u00cb\62\3\2\2\2\u00cc\u00cd\7"+
		"?\2\2\u00cd\64\3\2\2\2\u00ce\u00cf\7#\2\2\u00cf\u00d0\7?\2\2\u00d0\66"+
		"\3\2\2\2\u00d1\u00d2\7>\2\2\u00d2\u00d3\7?\2\2\u00d38\3\2\2\2\u00d4\u00d5"+
		"\7>\2\2\u00d5:\3\2\2\2\u00d6\u00d7\7@\2\2\u00d7<\3\2\2\2\u00d8\u00d9\7"+
		"@\2\2\u00d9\u00da\7?\2\2\u00da>\3\2\2\2\u00db\u00dc\7.\2\2\u00dc@\3\2"+
		"\2\2\u00dd\u00de\7(\2\2\u00deB\3\2\2\2\u00df\u00e0\7~\2\2\u00e0D\3\2\2"+
		"\2\u00e1\u00e2\7#\2\2\u00e2F\3\2\2\2\u00e3\u00e4\7`\2\2\u00e4H\3\2\2\2"+
		"\u00e5\u00e6\7=\2\2\u00e6J\3\2\2\2\u00e7\u00e8\7*\2\2\u00e8L\3\2\2\2\u00e9"+
		"\u00ea\7+\2\2\u00eaN\3\2\2\2\u00eb\u00ec\7]\2\2\u00ecP\3\2\2\2\u00ed\u00ee"+
		"\7_\2\2\u00eeR\3\2\2\2\u00ef\u00f1\t\3\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f2"+
		"\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4"+
		"\u00f5\b*\2\2\u00f5T\3\2\2\2\u00f6\u00fc\7)\2\2\u00f7\u00f8\7)\2\2\u00f8"+
		"\u00fb\7)\2\2\u00f9\u00fb\n\4\2\2\u00fa\u00f7\3\2\2\2\u00fa\u00f9\3\2"+
		"\2\2\u00fb\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd"+
		"\u00ff\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0100\7)\2\2\u0100V\3\2\2\2\u0101"+
		"\u0105\7}\2\2\u0102\u0104\n\5\2\2\u0103\u0102\3\2\2\2\u0104\u0107\3\2"+
		"\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0108\3\2\2\2\u0107"+
		"\u0105\3\2\2\2\u0108\u0109\7\177\2\2\u0109\u010a\3\2\2\2\u010a\u010b\b"+
		",\2\2\u010bX\3\2\2\2\13\2\u00ad\u00b4\u00bc\u00be\u00f2\u00fa\u00fc\u0105"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}