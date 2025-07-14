package stringex;

import java.util.Calendar;
import java.util.Scanner;

public class StringEx02 {
    public static void main(String[] args) {
        String str="";
        Scanner kbd = new Scanner(System.in);
        do{
            System.out.println("문자열을 입력하세요:");
            str = kbd.nextLine();

            if(str.length() < 3) {
                System.out.println("문자열이 너무 작습니다.\n3자이상 입력");
                continue;
            }

            if(str.substring(0,3).equals("www")){
                System.out.println("www"+"로 시작합니다.");
            }else {
                System.out.println("www"+"로 시작하지 않습니다.");
            }

            if(str.equals("quit")) break;

        }while (true);

        System.out.println("프로그램 종료되었습니다.");

    }
}
