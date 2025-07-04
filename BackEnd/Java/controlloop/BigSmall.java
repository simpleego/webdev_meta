package controlloop;

import java.util.Scanner;

public class BigSmall {
    public static void main(String[] args) {

        // 정수 2개를 키보드로 입력 받아서 큰수/작은수를 판별하여 출력
        Scanner kbd;
        int num1, num2;
        String result="",s;

        // 키보드 객체 생성
        kbd = new Scanner(System.in);

        System.out.println("정수1 입력하세요.");
        s = kbd.nextLine();
        num1 = Integer.parseInt(s);

        System.out.println("정수2 입력하세요.");
        s = kbd.nextLine();
        num2 = Integer.parseInt(s);

        String msg = "num:"+num1;
        System.out.println(msg);

        if(num1 > num2){
           //result = "큰수:"+num1;
           result = "큰수:" + String.valueOf(num1);// 정수를 문자로변환하는 메서드
        }else {
            if(num2 > num1){
                result = "큰수:"+num2;
            }else {
                result = "두수는 같다";
            }
        }
        System.out.println(result);
    }
}
