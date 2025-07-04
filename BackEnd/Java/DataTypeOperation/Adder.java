package DataTypeOperation;

import java.util.Scanner;

public class Adder {
    public static void main(String[] args) {
        int num1, num2, sum;
        String str;
        Scanner keyboard;

        keyboard = new Scanner(System.in);

        System.out.print("숫자입력1 : ");
        str = keyboard.nextLine();
        num1 = Integer.parseInt(str);

        System.out.print("숫자입력1 : ");
        str = keyboard.nextLine();
        num2 = Integer.parseInt(str);

        sum = num1 + num2;

        System.out.println("숫자1 :"+num1);
        System.out.println("숫자2 :"+num2);
        System.out.println("합계 :"+sum);
    }
}
