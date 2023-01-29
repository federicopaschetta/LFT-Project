public class Es1_8 {

public static boolean scan (String s) {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
        case 0:
            if (ch == '+' || ch == '-') {
              state = 1;
            } else if (ch == '.') {
              state = 2;
            } else if (ch <= '9' && ch >= '0') {
              state = 3;
            } else {
              state = -1;
            }
        break;

        case 1:
            if (ch == '.') {
              state = 2;
            } else if (ch <= '9' && ch >= '0') {
              state = 3;
            } else {
              state=-1;
            }
        break;

        case 2:
            if (ch <= '9' && ch >= '0') {
              state = 4;
            } else {
              state = -1;
            }
      
        break;

        case 3:
            if (ch <= '9' && ch >= '0') {
              state = 3;
            } else if (ch == 'e') {
              state = 5;
            } else if (ch == '.') {
              state = 2;
            } else {
              state = -1;
            }
        break;

        case 4:
            if (ch <= '9' && ch >='0') {
              state = 4;
            } else if (ch == 'e') {
              state = 5;
            } else {
              state = -1;
            }
        break;

        case 5:
            if (ch == '+' || ch == '-') {
              state = 6;
            } else if (ch <='9' && ch >= '0') {
              state = 9;
            } else if (ch == '.') {
              state = 7;
            } else {
              state = -1;
            }
        break;

        case 6:
            if (ch <= '9' && ch >= '0') {
              state = 9;
            } else if (ch == '.') {
              state = 7;
            } else {
              state = -1;
            }
        break;

        case 7:
            if (ch <= '9' && ch >= '0') {
              state = 8;
            } else {
              state = -1;
            }
        break;

        case 8:
            if (ch <= '9' && ch >='0') {
              state=8;
            } else {
              state = -1;
            }
        break;

        case 9:
            if (ch <= '9' && ch >= '0') {
              state = 9;
            } else if (ch == '.') {
              state = 10;
            } else {
              state = -1;
            }
        break;

        case 10:
            if (ch <= '9' && ch >= '0') {
              state = 11;
            } else {
              state = -1;
            }
        break;

        case 11:
            if (ch <= '9' && ch >= '0') {
              state = 11;
            } else {
              state = -1;
            }
        break;   
      }
	  }
	return (state == 11 || state == 3 || state == 4 || state == 8 || state == 9);
  }

  public static void main(String[] args){
    System.out.println(scan(args[0])? "OK" : "NOPE");	
  }
}


