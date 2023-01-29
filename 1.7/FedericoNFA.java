public class Federico {

    public static boolean scan (String s) {
        String nome = "Federico";
        int i = 0;
        int state = 0;
        while (state>=0 && i<s.length()) {
            final char c = s.charAt(i);
            char lett = nome.charAt(i++);

            switch(state) {

                case 0:
                    if (c == lett) {
                        state = 0;
                    } else {
                        state = 1;
                    }
                break;


                case 1:
                    if (c == lett) {
                        state = 1;
                    } else {
                        state = -1;
                    }
                break;



            }


        }
        return (state == 0) || (state == 1);
    }
    
    public static void main (String [] args) {
        System.out.println(scan(args[0])? "OK" : "Nope");
    }
}
