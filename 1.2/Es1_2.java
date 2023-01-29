public class Es1_2 {
    public static boolean scan (String s) {
        int i = 0;
        int stato = 0;
        while (stato>=0 && i<s.length()) {
            final char c = s.charAt(i++);
                switch (stato) {

                    case 0:
                        if (c == '_') { // se inizia con _ va allo stato 1, con una lettera va al 2
                            stato = 1;
                        } else if ((c>=65 && c<=90) || (c>=97 && c<=122)) {
                            stato = 2;
                        } else { // altrimenti esce dal ciclo
                            stato = -1;
                        } break;

                    case 1:
                        if (c == '_') { // se continua con '_' rimane nello stesso stato
                            stato = 1;
                        } else if ((c>=65 && c<=90) || (c>=97 && c<=122) || (c>=48 && c<=57)) { // se continua con una lettera va nello stato 2
                            stato = 2; 
                        } else { // altrimenti esce dal ciclo
                            stato = -1;
                        } break;
                    
                    case 2: // stato finale
                        if ((c>=65 && c<=90) || (c>=97 && c<=122) || (c>=48 && c<=57) || (c=='_')) { // se dopo una lettera c'e' un numero/lettera/_ va bene
                            stato = 2;
                        } else { // altrimenti stato pozzo
                            stato = -1;
                        } break;
                }
        }
        return (stato == 2);
    }

    public static void main (String[]args) {
        System.out.println(scan(args[0]) ? "Va Bene" : "Non va bene");
    }
    
}
