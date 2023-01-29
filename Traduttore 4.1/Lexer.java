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

    /* Il metodo legge un nuovo carattere quando nella variabile peek ci sono i seguenti caratteri */

        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }

    /* Quando vengono letti i seguenti simboli viene ritornato il Token corrispondente e viene aggiornato peek a ' ', 
    in modo da leggere il carattere seguente e continuare l'iterazione */  

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
                        
            case ';':
                peek = ' ';
                return Token.semicolon;
    
            case ',':
                peek = ' ';
                return Token.comma;

    /* Quando viene letto '/', se seguito da '*', corrisponde all'inizio di un commento, quindi, fino alla sequenza '* /'
    deve ignorare i caratteri letti. Se invece '/' non e' seguito da '*' viene restituito il Token corrispondente. */

            case '/':
            {
                readch(br); 
                if (peek == '*') {
                    readch(br);
                    while(peek != '/') {
                        while (peek != '*') {
                            readch(br);
                        }
                    readch(br);
                    }
                    peek = ' ';
                    
                } else if (peek == '/') {
                    while (peek != '\n' && peek != (char)-1) {
                        readch(br);
                    }
                    
                } else {
                    return Token.div; 
                }
                return lexical_scan(br);
        }

    /* Quando viene letto '&', deve essere seguito da '&', altrimenti restituisce un errore. */

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

    /* Quando viene letto '|', deve essere seguito da '|', altrimenti restituisce un errore. */

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
        
    /* Quando viene letto '<', viene letto subito un ulteriore carattere, se quel carattere e' '=', corrisponde al Token '<=', e viene restituito,
    se quel carattere e' '>', corrisponde al Token '<>', e viene restituito, altrimenti restituisce il Token corrispondente a '<', senza fare variazioni
    alla variabile peek, poiche' in peek e' gia' presente il carattere successivo a '>'. */

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
            
    /* Quando viene letto '>', viene letto subito un ulteriore carattere, se quel carattere e' '=', corrisponde al Token '>=', e viene restituito,
    altrimenti restituisce il Token corrispondente a '<', senza fare variazioni
    alla variabile peek, poiche' in peek e' gia' presente il carattere successivo a '>'. */
            
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


	// ... gestire i casi di || < > <= >= == <> ...  v// 
    /* Se il carattere in peek e' -1, viene restituito il Token corrispondente alla fine del file. */
    
            case (char)-1:
                return new Token(Tag.EOF);
    
    /* Se il carattere e' una lettera o '_', puo' essere una parola chiave oppure un identificatore. Si utilizza la stringa di appoggio id, dichiarata 
    all'inizio, per immagazzinare il carattere in peek ad ogni iterazione e formare una stringa. La stringa viene poi confrontata con tutte le 
    parole chiave, se vi appartiene, viene restituito la corrispondente Word, se non vi appartiene, la stringa deve quindi rispettare le regole degli
    identificatori. Viene primacontrollato che la stringa id non sia composta solo da '_', restituendo in caso errore, e poi viene copiata nella stringa 
    ret, id viene portata nuovamente a stringa vuota e, utilizzando ret, viene restituita la Word corrispondente all'identificatore inserito. */
            default:
                if (Character.isLetter(peek)||(peek=='_')) {
                    for (;Character.isLetter(peek) || Character.isDigit(peek) || (peek=='_'); readch(br)) {
                        id = id + peek;
                    }
                    if (id.equals("end") || id.equals("assign") || id.equals("to") || 
                    id.equals("if") || id.equals("else") || id.equals("while") || 
                    id.equals("begin") || id.equals("print") || id.equals("read")) {
                        switch(id) {
                            case "end":
                                id = "";
                                return Word.end;

                            case "assign":
                                id = "";
                                return Word.assign;
                            
                            case "to":
                                id = "";
                                return Word.to;
                            
                            case "if":
                                id = "";
                                return Word.iftok;
                            
                            case "else":
                                id = "";
                                return Word.elsetok;

                            case "while":
                                id = "";
                                return Word.whiletok;

                            case "begin":
                                id = "";
                                return Word.begin;

                            case "print":
                                id = "";
                                return Word.print;

                            case "read":
                                id = "";
                                return Word.read;
                        }
                        
                    } else {
                        int i;
                        for (i = 0;i<id.length() && id.charAt(i)=='_'; i++) {}
                        if(i==id.length()) {
                            System.out.println("Errore!");
                            return null;
                        } else {
                        ret = id;
                        id = "";
                        return new Word(257, ret);
                        }
                    }
                
	// ... gestire il caso degli identificatori e delle parole chiave //

    /* Se il carattere e' un numero viene accumulato nella stringa ret e viene ritornato il NumberTok corrispondente. */

                } else if (Character.isDigit(peek)) {
                    for (; Character.isDigit(peek);readch(br)) {
                        id = id + peek;
                    }
                    ret = id;
                    id = "";
                    return new NumberTok(256, Integer.parseInt(ret));

	// ... gestire il caso dei numeri ... //

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
        String path = "testo.txt"; // il percorso del file da leggere
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
