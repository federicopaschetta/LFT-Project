public class Es1_5 {
    
    public static boolean scan (String s) {
        int i = 0;
        int state = 0;
        while (state>=0 && i<s.length()) {
            final char c = s.charAt(i++);
            switch (state) {
                case 0: // stato iniziale
                    if (c>=65 && c<=75) { // se A-K va nello stato 1
                        state = 1;
                    } else if ((c>=76 && c<=90)) { // se L-Z va nello stato 2
                        state = 2;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 1: // inizia con A-K
                    if (c>=97 && c<=122) { // se lettera minuscola, rimane nello stesso stato
                        state = 1;
                    } else if (c == ' ') { // se ' ', va nello stato 3
                        state = 3;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se numero pari, va nello stato 6
                        state = 6;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se numero dispari, va nello stato 5
                        state = 5;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 2: // inizia con L-Z
                    if (c>=97 && c<=122) { // se lettera minuscola, rimane nello stesso stato
                        state = 2;
                    } else if (c == ' ') { // se ' ', va nello stato 4
                        state = 4;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se numero pari, va nello stato 8
                        state = 8;
                    } else if (((c>=48 && c<=57) && ((c-'0')%2)==1)) { // se numero dispari, va nello stato 7
                        state = 7;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 3: // stringa con A-K+' '
                    if (c>=65 && c<=90) { // A-Z, va nello stato 1
                        state = 1;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 4: // stringa con L-Z+' '
                    if (c>=65 && c<=90) { // A-Z, va nello stato 2
                        state = 2;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 5: // stringa con A-K + numero dispari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se num dispari, rimane nello stesso stato
                        state = 5;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se num pari, va nello stato 6
                        state = 6;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 6: // stato finale->stringa con A-K + numero pari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==1) {  // se num dispari, va nello stato 5
                        state = 5;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) {  // se num pari, rimane nello stesso stato
                        state = 6;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 7: // stato finale-> stringa con L-Z + num dispari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se num dispari, rimani nello stesso stato
                        state = 7;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se num pari, va nello stato 8
                        state = 8;
                    } else {  // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 8: // stringa con L-Z + num pari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se num dispari, va nello stato 7
                        state = 7;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se num pari, rimane nello stesso stato
                        state = 8;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;
            }
        }
        return (state==6)||(state==7);
    }

    public static void main (String [] args) {
        System.out.println(scan(args[0])? "OK" : "Nope");
    }
}
