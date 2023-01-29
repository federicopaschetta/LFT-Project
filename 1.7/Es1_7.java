public class Es1_7 {
    public static void main (String[]args) {
        System.out.println(scan("Fwderico")? "OK" : "Nope");
    }

    public static boolean scan (String s) {

        int i = 0;
        int state = 0;
        while (state>=0 && i<s.length()) {
            final char c = s.charAt(i++);
            switch(state) {

                case 0:
                if (c == 'F') {
                        state = 1;
                    } else {
                        state = 8;
                }
                break;

                case 1:
                if (c == 'e') {
                    state = 2;
                } else {
                    state = 9;
                }
                break;

                case 2:
                if (c == 'd') {
                    state = 3;
                } else {
                    state = 10;
                }
                break;

                case 3:
                if (c == 'e') {
                    state = 4;
                } else {
                    state = 11;
                }
                break;

                case 4:
                if (c == 'r') {
                    state = 5;
                } else {
                    state = 12;
                }
                break;

                case 5:
                if (c == 'i') {
                    state = 6;
                } else {
                    state = 13;
                }
                break;

                case 6:
                if (c == 'c') {
                    state = 7;
                } else {
                    state = 14;
                }
                break;

                case 7:
                state = 15;
                break;

                case 8:
                if (c == 'e') {
                    state = 9;
                } else {
                    state = -1;
                }
                break;

                case 9:
                if (c == 'd') {
                    state = 10;
                } else {
                    state = -1;
                }
                break;

                case 10:
                if (c == 'e') {
                    state = 11;
                } else {
                    state = -1;
                }
                break;

                case 11:
                if (c == 'r') {
                    state = 12;
                } else {
                    state = -1;
                }
                break;

                case 12:
                if (c == 'i') {
                    state = 13;
                } else {
                    state = -1;
                }
                break;

                case 13:
                if (c == 'c') {
                    state = 14;
                } else {
                    state = -1;
                }
                break;

                case 14:
                if (c == 'o') {
                    state = 15;
                } else {
                    state = -1;
                }
                break;

                case 15:
                    state = -1;
                break;


            }
        }

        return state == 15;
    }
}
