package controlloop;

import java.util.Scanner;

public class NumberToString {
    public static void main(String[] args) {
        // 점수를 입력 받아서 한글로 말한다.
        Scanner kbd;
        int num;
        String korean="";

        kbd = new Scanner(System.in);

        System.out.println("정수를 입력 : ");
        num = Integer.parseInt(kbd.nextLine());

        switch (num){
            case 1:
                korean="하나";
                break;
            case 2:
                korean="둘";
                break;
            case 3:
                korean="셋";
                break;
            default:
                korean="모르는 숫자입니다.";
        }
        System.out.println("숫자 : "+num+"=>"+korean);
    }
}
