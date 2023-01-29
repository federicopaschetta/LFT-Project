import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
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


    private void prog() {
        switch(look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.IF:
            case '{':
                statlist();
                match(Tag.EOF);
                break;
            default:
                error("Parser->prog()");
        }
    }

    private void statlist() {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.IF:
            case '{':
                stat();
                statlistp();
                break;

            default:
                error("Parser->statlist()");
        }
    }

    private void statlistp() {
        switch(look.tag) {
            case ';':
                match(';');
                stat();
                statlistp();
            
            case '}':
            case Tag.EOF:
                break;
            
            default:
                error("Parser->statlistp()");
        }
    }

    private void stat() {
        switch(look.tag) {
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist();
                break;

            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist();
                match(')');
                break;

            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist();
                match(')');
                break;

            case Tag.WHILE:
                match(Tag.WHILE);
                match('(');
                bexpr();
                match(')');
                stat();
                break;

            case Tag.IF:
                match(Tag.IF);
                match('(');
                bexpr();
                match(')');
                stat();
                stat2();
                break;

            case '{':
                match('{');
                statlist();
                match('}');
                break;

            default:
                error("Parser->stat()");
        }
    }

    private void stat2 () {
        switch(look.tag) {
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
                match(Tag.END);
                break;
                
            case Tag.END:
                match(Tag.END);
                break;
                
            default:
            error("Parser->stat2()");

        }
    }
    

    private void idlist() {
        switch(look.tag) {
            case Tag.ID:
                match(Tag.ID);
                idlistp();
                break;
                
            default:
            error("Parser->idlist()");
        }
        
    }

    private void idlistp() {
        switch(look.tag) {
            case ',':
                match(',');
                match(Tag.ID);
                idlistp();
                break;
            
            case ')':
            case ';':
            case Tag.END:
            case Tag.ELSE:
            case Tag.EOF:
            case '}':
                break;

            default:
                error("Parser->idlistp()");
        }
        
    }

    private void bexpr() {
        switch(look.tag) {
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;
            
            default:
                error("Parser->bexpr()");
        }
        
    }

    private void expr() {
        switch(look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist();
                match(')');
                break;

            case '-':
                match('-');
                expr();
                expr();
                break; 

            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                break;

            case '/':
                match('/');
                expr();
                expr();
                break; 

            case Tag.NUM:
                match(Tag.NUM);
                break;

            case Tag.ID:
                match(Tag.ID);
                break;

            default:
                error("Parser->expr()");
        }
        
    }

    private void exprlist() {
        switch(look.tag) {
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
            expr();
            exprlistp();
            break;

            default:
                error("Parser->exprlist()");
        }
    }

    private void exprlistp() {
        switch(look.tag) {
            case ',':
                match(',');
                expr();
                exprlistp();
                break;

            case ')':
                break;

            default:
                error("Parser->exprlistp()");
        }
        
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "max_tre_num.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
/* 
FIRST:

FIRST(P) = FIRST(Sl) = FIRST(S) = assign | print | read | while | if | {
FIRST(Sl) = FIRST(S) = assign | print | read | while | if | {
FIRST(Sl') = ;
FIRST(S) = assign | print | read | while | if | {
FIRST(I) = ID
FIRST(I') = ,
FIRST(B) = RELOP
FIRST(E) = +, -, *, /, NUM, ID
FIRST(El) = FIRST(E) = +, -, *, /, NUM, ID
FIRST(El') = ,

FOLLOW:

$ e FOLLOW(P)
FIRST(EOF) e FOLLOW(Sl)
FIRST(Sl') e FOLLOW(S)
FOLLOW(Sl) e FOLLOW(S)
FOLLOW(Sl) e FOLLOW(Sl')
to e FOLLOW(E)
FOLLOW(S) e FOLLOW(I)
) e FOLLOW(El)
) e FOLLOW(I)
) e FOLLOW(B)
end e FOLLOW(S)
else e FOLLOW(S)
} e FOLLOW(Sl)
FOLLOW(I) e FOLLOW(Il')
FIRST(E) e FOLLOW(E)
FOLLOW(B) e FOLLOW(E)
) e FOLLOW(El)
FIRST(El') e FOLLOW(E)
FOLLOW(El) e FOLLOW(El')

P -> $
Sl -> EOF, }
Sl' -> EOF, }
S -> ;, end, else, EOF, }
I -> ), ;, end, else, EOF, }
Il' -> ), ;, end, else, EOF, }
B -> )
E -> to, +, -, *, /, NUM, ID, ,, )
El -> )
El' -> )

INSIEMI GUIDA:

P -> Sl EOF = FIRST(Sl) = assign | print | read | while | if | {
Sl -> S Sl' = FIRST(S) = assign | print | read | while | if | {
Sl' -> ; S Sl' = FIRST(;) = ;
Sl' -> eps = FOLLOW(Sl') = EOF, {
S -> assign E to Il -> FIRST(assign) = assign
    | print (El) -> FIRST(print) = print
    | read (I) -> FIRST(read) = read
    | while (B) S -> FIRST(while) = while
    | if (B) S end -> FIRST(if) = if               NO
    | if (B) S else S end -> FIRST(if) = if
    | {Sl} -> FIRST({) = {
I -> ID I' = FIRST(ID) = ID
Il' -> , ID I' = FIRST(,) = ,
Il' -> eps = FOLLOW(Il') = ), ;, end, else, EOF, }
B -> RELOP E E = FIRST(RELOP) = RELOP
E -> +(El) -> FIRST(+) = +
    | -E E -> FIRST(-) = - 
    | *(El) -> FIRST(*) = *
    | /E E -> FIRST(/) = /
    | NUM -> FIRST(NUM) = NUM
    | ID -> FIRST(ID) = ID
El -> E El' = FIRST(E) = +, -, *, /, NUM, ID
El' -> , E El' = FIRST(,) = ,
El' -> eps = FOLLOW(El') = )

*/

