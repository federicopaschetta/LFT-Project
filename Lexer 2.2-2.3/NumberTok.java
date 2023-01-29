public class NumberTok extends Token {
	public int lexeme;
	public NumberTok(int tag, int num) { super(tag); lexeme=num; }
	public String toString() {return "<" + tag + ", " + lexeme + ">";}
	
}
