public class Es1_3 {

    public static boolean scan (String s) {
        int i = 0;
        int state = 0;
        while (state>=0 && i<s.length()) {
            final char c = s.charAt(i++);

            switch (state) {
                case 0: // stato iniziale
                    if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se inizia con un numero pari, va nello stato 1
                        state = 1;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se inizia con un numero dispari, va nello stato 2
                        state = 2;
                    } else { // altrimenti va nello stato pozzo
                        state = -1;
                    }
                break;

                case 1: // stringa che inizia con un numero pari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se continua con un numero pari rimane nello stesso stato
                        state = 1;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se continua con un numero dispari va nello stesso stato 2
                        state = 2;
                    } else if (c>=65 && c<=75) {// se continua con una lettera A-K va nello stato 3
                        state = 3;
                    } else { // altrimenti va nello stato pozzo
                        state = -1;
                    }
                break;

                case 2: // stringa che inizia con un numero dispari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==0) {  // se continua con un numero pari va nello stesso stato 1
                        state = 1;
                    } else if ((c>=48 && c<=57) && ((c-'0')/2)==1) { // se continua con un numero dispari rimane nello stesso stato
                        state = 2;
                    } else if (c>=76 && c<=90) { // se continua con una lettera L-Z va nello stato 3
                        state = 3;
                    } else {
                        state = -1; // altrimenti va nello stato pozzo
                    }
                break;

                case 3: // stato finale -> pari+A-K/dispari+L-Z
                    if (c>=97 && c<=122) { // se continua con una lettera a-z rimane nello stesso stato
                        state = 3;
                    } else { // altrimenti va nello stato pozzo
                        state = -1;
                    }
                break;

            }
        }
        return (state == 3);
    }

    public static void main (String [] args) {
        System.out.println(scan(args[0])? "OK" : "Nope");
    }
}
