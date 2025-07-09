package controlloop;

import java.util.Scanner;

public class For01 {
    public static void main(String[] args) {
        int fact = 1;
        int n;

        System.out.println("정수를 입력하세요");
        Scanner kbd = new Scanner(System.in);
        n = kbd.nextInt();

        for(int i=1; i<=n; i++){
            fact = fact * i;
            if(fact<0) {
                System.out.println("i:"+i);
                System.out.println("fact:"+fact);
            }
        }
        System.out.printf("%d!은 %d입니다.",n,fact);
    }
}
