public class Es1_4 {
    
    public static boolean scan (String s) {
        int i = 0;
        int state = 0;
        while (state >=0 && i<s.length()) {
            char c = s.charAt(i++);
            switch(state) {
                case 0: // stato iniziale
                    if ((c>=48 && c<=57) && ((c-'0')%2)==0) { //se inizia con un numero pari va nello stato 1
                        state = 1;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==1) { //se inizia con un numero dispari va nello stato 2
                        state = 2;
                    } else if (c == ' ') { // se inizia con uno spazio rimane nello stesso stato
                        state = 0;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 1: // inizia con numero pari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se numero pari, rimane nello stesso stato
                        state = 1;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se numero dispari, va nello stato 2
                        state = 2;
                    } else if (c>=65 && c<=75) { // se A-K, va nello stato 3
                        state = 3;
                    } else if (c == ' ') { // se ' ', va nello stato 4
                        state = 4;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 2: // inzia con un numero dispari
                    if ((c>=48 && c<=57) && ((c-'0')%2)==1) { // se numero dispari, rimane nello stesso stato
                        state = 2;
                    } else if ((c>=48 && c<=57) && ((c-'0')%2)==0) { // se numero pari, va nello stato 1
                        state = 1;
                    } else if (c>=76 && c<=90) { // se L-Z, va nello stato 3
                        state = 3;
                    } else if (c == ' ') { // se ' ', va nello stato 5
                        state = 5;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 3: // stato finale -> numero pari+A-K/dispari+L-Z
                    if (c>=97 && c<=122) { // se lettera minuscola, rimane nello stesso stato
                        state = 3;
                    } else if (c == ' ') { // se ' ', va  nello stato 6
                        state = 6;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 4: // numero pari + ' '
                    if (c == ' ') { // se ' ', rimane nello stesso stato
                        state = 4;
                    } else if (c>=65 && c<=75) { // se A-K, va nello stato 3
                        state = 3;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 5: // numero dispari + ' '
                    if (c == ' ') { // se ' ', rimane nello stesso stato
                        state = 5;
                    } else if (c>=76 && c<=90) { // se L-Z, va nello stato 3
                        state = 3;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;

                case 6: // stato finale -> numero pari+A-K/dispari+L-Z+' '
                    if (c == ' ') { // se ' ', rimane nello stesso stato
                        state = 6;
                    } else if (c >= 65 && c<=90) { // se lettera maiuscola, va nello stato 3
                        state = 3;
                    } else { // altrimenti stato pozzo
                        state = -1;
                    }
                break;
            }
        }
        return (state == 3) || (state == 6);
    }




    public static void main (String[] args) {
        System.out.println(scan("654321 Rossi")? "OK" : "Nope");
    }
}
