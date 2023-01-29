import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    String id = "";
    String ret = "";
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
            
            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
            peek = ' ';
            return Token.rpt;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
            peek = ' ';
                return Token.plus;

            case '-':
            peek = ' ';
                return Token.minus;

            case '*':
            peek = ' ';
                return Token.mult;

            case '/':
            peek = ' ';
                return Token.div;
            
            case ';':
            peek = ' ';
                return Token.semicolon;

            case ',':
            peek = ' ';
                return Token.comma;
            


	
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character" 
                            + "after | : " + peek );
                    return null;
                }

            
            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if (peek == '>') { 
                    peek = ' ';
                    return Word.ne;
                } else {
                    return Word.lt;
                }
            
            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else {
                    return Word.gt;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                }


            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek)) {
                    for (;Character.isLetter(peek) || Character.isDigit(peek);readch(br)) {
                        id = id + peek;
                    }
                    if (id.equals("end") || id.equals("assign") || id.equals("to") || 
                    id.equals("if") || id.equals("else") || id.equals("while") || 
                    id.equals("begin") || id.equals("print") || id.equals("read")) {
                        switch(id) {
                            case "end":
                                peek = ' ';
                                id = "";
                                return Word.end;

                            case "assign":
                                peek = ' ';
                                id = "";
                                return Word.assign;
                            
                            case "to":
                                peek = ' ';
                                id = "";
                                return Word.to;
                            
                            case "if":
                                peek = ' ';
                                id = "";
                                return Word.iftok;
                            
                            case "else":
                                peek = ' ';
                                id = "";
                                return Word.elsetok;

                            case "while":
                                peek = ' ';
                                id = "";
                                return Word.whiletok;

                            case "begin":
                                peek = ' ';
                                id = "";
                                return Word.begin;

                            case "print":
                                peek = ' ';
                                id = "";
                                return Word.print;

                            case "read":
                                peek = ' ';
                                id = "";
                                return Word.read;
                        }
                        
                    } else {
                        ret = id;
                        id = "";
                        return new Word(257, ret);
                    }
                

                } else if (Character.isDigit(peek)) {
                    for (; Character.isDigit(peek);readch(br)) {
                        id = id + peek;
                    }
                    ret = id;
                    id = "";
                    return new NumberTok(256, Integer.parseInt(ret));
                
                /*

                Un altro modo per riconoscere i numeri:
                
                else if (Character.isDigit(peek)) {
                    for (int i = 1; Character.isDigit(peek); readch(br, i*10)) {
                        tmp = tmp + ((int)peek)*i;
                    }
                    tot = tmp;
                    tmp = 0;
                    return new NumberTok(256, tmp);
                }
                */


                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
         return null;
    }

		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "testo.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}
