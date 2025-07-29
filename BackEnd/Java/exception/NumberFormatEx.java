package exception;

import java.util.Scanner;

public class NumberFormatEx {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);

        System.out.println("점수 3자리 입력");
        String str = kbd.nextLine();
        int num=0;
        try {
            num = Integer.parseInt(str);
        }catch (NumberFormatException e){
            System.out.println("숫자를 입력하세요.");
            num = 10;
        }
        System.out.println("점수 : "+num);
    }
}
