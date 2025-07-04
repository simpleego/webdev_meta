package controlloop;

import java.util.Scanner;

public class Bonus {
    public static void main(String[] args) {
        // 목표실적(1000)을 달성하면 초과수당10%를 보너스로 지급한다.
        // 초과실적에 해당하는 보너스 금액을 구하시오.
        Scanner kbd;
        int mySales;
        int bonus;
        final int TARGET_SALES=1000;

        // 키보드 객체 생성
        kbd = new Scanner(System.in);

        System.out.println("실적 입력하세요.");
        mySales = Integer.parseInt(kbd.nextLine());

        int sales = mySales - TARGET_SALES;

        if(sales > 0){
            System.out.println("실적달성 "+"보너스:"+(sales/10));
        }else {
            System.out.println("실적달성 못함"+"보너스: 0");
        }
    }
}
