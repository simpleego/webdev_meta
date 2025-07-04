package controlloop;

import java.util.Scanner;

public class EvenOdd1 {
    public static void main(String[] args) {

        // 정수를 키보드로 입력 받아서 짝수/홀수를 판별하여 출력
        Scanner kbd;
        int num1;
        String result="홀수";

        // 키보드 객체 생성
        kbd = new Scanner(System.in);

        System.out.println("정수입력하세요.");
        String s = kbd.nextLine();
        num1 = Integer.parseInt(s);

        String msg = "num:"+num1;
        System.out.println(msg);

        int num2 = num1 % 2;

        if(num2 == 0){
           result = "짝수";
        }
        System.out.println(result);
    }

}
