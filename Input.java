import java.util.*;
public class Input {

    public static String getString(String text) {
        System.out.println(text);
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }  

    public static int getInt(String text) {
        System.out.println(text);
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
}



