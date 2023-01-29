import java.io.*; 

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) { 
	lex = l; 
	pbr = br;
	move(); 
    }

    void move() { 
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) { 
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error");
    }

    /* 
    <start> -> <expr> EOF {print(expr_val)}
    */

    public void start() { 
        int expr_val;
        switch(look.tag){
            case '(':
            case Tag.NUM:
                expr_val = expr();
                match(Tag.EOF);
                System.out.print("Il risultato e': ");
                System.out.println(expr_val); 
                break;       
            default:
                error("start()");
        }
    }

    /*
    <expr> -> <term> {exprp_i = term_val} <exprp> {expr_val = exprp_val}
    */

    private int expr() { 
        int term_val, exprp_val;
        switch(look.tag) {
            case '(':
            case Tag.NUM:
                term_val = term();
                exprp_val = exprp(term_val);
                return exprp_val;
            
            default:
                error("expr()");
                return 0;
        }
    }

    /* <exprp> -> + <term> {exprp1_val = exprp_i + term_val} <exprp1> {exprp_val = exprp1_val}
                    | eps {exprp_val = exprp_i}
    */
    private int exprp(int exprp_i) {
        int term_val, exprp_val;
        switch (look.tag) {
        case '+':
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                return exprp_val;

        case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                return exprp_val;

        case ')':
        case Tag.EOF:
                exprp_val = exprp_i;
                return exprp_val;
        
        default:
            error("exprp()");
            return 0;
        }
    }

    // <term> -> <fact> {termp_i = fact_val} <termp> {term_val = termp_val}

    private int term() { 
        int fact_val, term_val;
        switch(look.tag) {
            case '(':
            case Tag.NUM:
                fact_val = fact();
                term_val = termp(fact_val);
                return term_val;

            default:
                error("term()");
                return 0;


        }
    }

    /* <termp> -> * <fact> {termp1_i = termp_i * fact_val} <termp1> {termp_val = termp1_val}
                    | eps {termp_val = termp_i}
    */
    
    private int termp(int termp_i) { 
        int fact_val, termp_val;
        switch(look.tag) {
            case '*':
                match('*');
                    fact_val = fact();
                    termp_val = termp(termp_i*fact_val);
                    return termp_val;

            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i/fact_val);
                return termp_val;

            case Tag.EOF:
            case ')':
            case '+':
            case '-':
                termp_val = termp_i;
                return termp_i;

            default:
                error("termp()");
                return 0;
        }
    }
    
    // <fact> -> (<expr>) {fact.val = expr.val} | NUM {fact.val = NUM.val}

    private int fact() { 
        int fact_val;
        switch(look.tag) {
            case '(':
                match('(');
                fact_val = expr();
                match(')');
                return fact_val;

            case Tag.NUM:
                fact_val = ((NumberTok)look).lexeme;
                match(Tag.NUM);
                return fact_val;

            default:
                error("fact()");
                return 0;
        }    
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "testo.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}