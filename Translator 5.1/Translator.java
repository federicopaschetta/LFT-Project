import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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
            if (look.tag != Tag.EOF) 
                move();
        } else 
            error("syntax error");    }

    public void prog() {        
        switch(look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.IF:
            case '{':
            int lnext_prog = code.newLabel();
            statlist(lnext_prog);
            code.emit(OpCode.GOto, lnext_prog);
            code.emitLabel(lnext_prog);
            match(Tag.EOF);
            try {
                code.toJasmin();
            }
            catch(java.io.IOException e) {
                System.out.println("IO error\n");
            };
            break;


            default:
                error("Translator->prog()");
        }
    }

    private void statlist(int lnext_statlist) {
	    switch (look.tag) {
	        case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.IF:
            case '{':
                int snext = code.newLabel();
                stat(snext);
                code.emitLabel(snext);
                statlistp(lnext_statlist);
                break;

            default:
                error("Translator->statlist()");
	    }
    }

    private void statlistp(int lnext_statlistp) {
        switch(look.tag) {
            case ';':
                match(';');
                int statnext = code.newLabel();
                stat(statnext);
                code.emitLabel(statnext);
                statlistp(lnext_statlistp);
                break;
            
            case '}':
            case Tag.EOF:
                break;
            
            default:
                error("Translator->statlistp()");
        }
    }

    private void stat(int lnext_stat) {
        int lnext_true, lnext_false;
        switch(look.tag) {

            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist(lnext_stat, 4);
                code.emit(OpCode.GOto, lnext_stat);
                break;

            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist(1);
                match(')');
                code.emit(OpCode.GOto, lnext_stat);
                break;

            case Tag.READ:
                match(Tag.READ);
                code.emit(OpCode.invokestatic, 0);
                match('(');
                idlist(lnext_stat, 0);
                match(')');
                code.emit(OpCode.GOto, lnext_stat);
                break;

            case Tag.WHILE:
                match(Tag.WHILE);
                match('(');
                int loop_start = code.newLabel();
                code.emitLabel(loop_start);
                lnext_true = code.newLabel();
                lnext_false = lnext_stat;
                bexpr(lnext_true);
                code.emit(OpCode.GOto, lnext_false);
                code.emitLabel(lnext_true);
                match(')');
                stat(loop_start);
                code.emit(OpCode.GOto, loop_start);
                break;

            case Tag.IF:
                match(Tag.IF);
                match('(');
                lnext_true = code.newLabel();
                lnext_false = code.newLabel();
                bexpr(lnext_true);
                code.emit(OpCode.GOto, lnext_false);
                code.emitLabel(lnext_true);
                match(')');
                stat(lnext_stat); 
                code.emit(OpCode.GOto, lnext_stat);
                code.emitLabel(lnext_false);
                stat2(lnext_stat);
                break;

            case '{':
                match('{');
                statlist(lnext_stat);
                match('}');
                break;

            default:
                error("Translator->stat()");
        }
    }

    private void stat2 (int lnext_stat2) {
        switch(look.tag) {
            case Tag.ELSE:
                match(Tag.ELSE);
                stat(lnext_stat2);
                match(Tag.END);
                break;
                
            case Tag.END:
                match(Tag.END);
                break;

            default:
                error("Translator->stat2()");
        }
    }

    private void idlist(int lnext_idlist, int num) {
        switch(look.tag) {
        case Tag.ID:
            int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                if (num == 4) {
                    code.emit(OpCode.dup);
                    code.emit(OpCode.istore, id_addr);
                    match(Tag.ID);
                    idlistp(lnext_idlist, num);
                    code.emit(OpCode.pop);
                } else {
                    code.emit(OpCode.istore, id_addr);
                    match(Tag.ID);
                    idlistp(lnext_idlist, num);
                }
                break;

            default:
                error("Translator->idlist()");
        }
    }

    private void idlistp(int lnext_idlistp, int num) {
        switch(look.tag) {
            case ',':
                match(',');
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                        id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }
                if (num == 4) {
                    code.emit(OpCode.dup);
                    code.emit(OpCode.istore, id_addr);
                    match(Tag.ID);
                    idlistp(lnext_idlistp, num);
                } else {
                    code.emit(OpCode.invokestatic, 0);
                    code.emit(OpCode.istore, id_addr);
                    match(Tag.ID);
                    idlistp(lnext_idlistp, num);
                }
                break;
            
            case ')':
            case ';':
            case Tag.END:
            case Tag.ELSE:
            case Tag.EOF:
            case '}':
                break;

            default:
                error("Translator->idlistp()");
        }
        
    }

    private void bexpr(int ltrue) {
        if(look.tag == Tag.RELOP) {
            switch(((Word)look).lexeme) {
                case "<":
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmplt, ltrue);
                break;

            case ">":
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpgt, ltrue);
                break;

            case "==":
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpeq, ltrue);
                break;

            case "<=":
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmple, ltrue);
                break;

            case ">=" :
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpge, ltrue);
                break;

            case "<>":
                match(Tag.RELOP);
                expr();
                expr();
                code.emit(OpCode.if_icmpne, ltrue);
                break;

            default:
                error("Translator->bexpr()");
            }
        }
            
        
    }

    private void expr() {
        switch(look.tag) {

            case '+':
                match('+');
                match('(');
                exprlist(2);
                match(')');
                break;

            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;

            case '*':
                match('*');
                match('(');
                exprlist(3);
                match(')');
                break;


                case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            case Tag.NUM:
                int id_num = ((NumberTok)look).lexeme;
                code.emit(OpCode.ldc, id_num);
                match(Tag.NUM);
                break;

            case Tag.ID:
                int id_address = st.lookupAddress(((Word)look).lexeme);
                if (id_address == -1) {
                    id_address = count;
                    st.insert(((Word)look).lexeme, count++);
                }
                code.emit(OpCode.iload, id_address);
                match(Tag.ID);

                break;

            default:
                error("Translator->expr()");
        }
    }

    private void exprlist(int num) {
        switch(look.tag) {
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
            expr();
            if (num == 1) {
                code.emit(OpCode.invokestatic, 1);
            }
            exprlistp(num);
            break;

            default:
                error("Translator->exprlist()");
        }
    }

    private void exprlistp(int num) {
        switch(look.tag) {
            case ',':
                match(',');
                expr();
                if (num == 1) {
                    code.emit(OpCode.invokestatic, 1);
                } else if (num == 2) {
                    code.emit(OpCode.iadd);
                } else if (num == 3) {
                    code.emit(OpCode.imul);
                }
                exprlistp(num);
                break;

            case ')':
                break;

            default:
                error("Translator->exprlistp()");
        }
        
    }

    public static void main (String [] args) {
        Lexer lex = new Lexer();
        String path = "testo.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader (path));
            Translator trans = new Translator (lex, br);
            trans.prog();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
