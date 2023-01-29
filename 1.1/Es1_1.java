public class Es1_1 {

    public static boolean scan (String s) 
    {
        int i = 0;
        int stato = 0;

        while (stato>=0 && i<s.length()) {
            final char c = s.charAt(i++);
            switch(stato) {
                case 0:
                    if (c == '0') {
                        stato = 1;
                    } else if (c == '1') {
                        stato = 0;
                    } else {
                        stato = -1;
                    } break;

                case 1:
                    if (c == '0') {
                        stato = 2;
                    } else if (c == '1') {
                        stato = 0;
                    } else {
                        stato = -1;
                    }
                    break;
                case 2:
                    if (c == '0') {
                        stato = 3;
                    } else if (c == '1') {
                        stato = 0;
                    } else {
                        stato = -1;
                    }
                    break;
            }
        }
        return (stato != 3);
    }
    public static void main (String [] args) {
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}

