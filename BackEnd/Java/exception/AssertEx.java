package exception;

import java.util.Scanner;

public class AssertEx {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        System.out.println("날짜를 입력하세요:");
        int date = kbd.nextInt();

        //날짜가 1이상이고 31이하인지 검증한다.
        assert(date >= 1 && date <= 31) : "잘못된 날짜"+date;
        System.out.printf("입력된 날짜는 %d입니다.",date);

    }
}
