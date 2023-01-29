public class Es1_6 {
    
    public static boolean scan (String s) {

        int i = 0;
        int state = 0;
        while (state>=0 && i<s.length()) {
            final char c = s.charAt(i++);
            boolean pari = ((c>=48 && c<=57) && ((c%2)==0));
            boolean dispari = ((c>=48 && c<=57) && ((c%2)==1));
            boolean ak = (c>=65 && c<=75);
            boolean lz = ((c>=76 && c<=90));
            boolean letteram = (c>=97 && c<=122);

            switch(state) {

                case 0:
                if (dispari) { 
                    state = 1;
                } else if (pari) {
                    state = 2;
                } else {
                    state = -1;
                }
                break;

                case 1:
                    if (dispari) {
                        state = 3;
                    } else if (pari) {
                        state = 4;
                    } else {
                        state = -1;
                    }
                break;

                case 2:
                    if (dispari) {
                        state = 5;
                    } else if (pari) {
                        state = 6;
                    } else {
                        state = -1;
                    }
                break;

                case 3:
                    if (dispari) {
                        state = 3;
                    } else if (pari) {
                        state = 4;
                    } else {
                        state = -1;
                    }
                break;

                case 4:
                    if (dispari) {
                        state = 5;
                    } else if (pari) {
                        state = 6;
                    } else if (lz) {
                        state = 7;
                    } else {
                        state = -1;
                    }
                break;

                case 5:
                    if (dispari) {
                        state = 3;
                    } else if (pari) {
                        state = 4;
                    } else if (ak) {
                        state = 7;
                    } else {
                        state = -1;
                    }
                break;

                case 6:
                    if (dispari) {
                        state = 5;
                    } else if (pari) {
                        state = 6;
                    } else {
                        state = -1;
                    }
                break;

                case 7:
                    if (letteram) {
                        state = 7;
                    } else {
                        state = -1;
                    }
                break;

            }

        }

        return (state==7);
        
    }

    public static void main (String[]args) {
        System.out.println(scan(args[0])? "OK" : "Nope");
    }
}
