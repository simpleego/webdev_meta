package DataTypeOperation;

import java.util.Scanner;



public class KeyboardInput {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        System.out.println("숫자입력 : ");
        String str = keyboard.nextLine();
        int num1 = Integer.parseInt(str);

        System.out.println("숫자 :"+(num1+10));

    }
}
