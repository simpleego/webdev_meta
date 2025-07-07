package controlloop;

import java.util.Scanner;

public class Hakjum {
    public static void main(String[] args) {
        // 점수를 입력 받아서 학점을 구한다.
        // 학점은 A,B,C 등급으로 출력한다.
        Scanner kbd;
        int jumsu;
        String hakjum="";

        kbd = new Scanner(System.in);

        System.out.println("성적을 입력 : ");
        String jumsu_ = kbd.nextLine();
        boolean isNumber = true;

        for (char c : jumsu_.toCharArray()) {
            if (!Character.isDigit(c)) {
                isNumber = false;
                break;
            }
        }

        if(isNumber) {
            jumsu = Integer.parseInt(jumsu_);

            // 학점판단
            if(jumsu >= 90){
                hakjum = "A";
            }else if(jumsu >=80){
                hakjum = "B";
            } else if (jumsu >70) {
                hakjum = "C";
            }

        }else {
            System.out.println("정수를 다시 입력하세요.");
            return;
        }

        System.out.println("성적 : "+jumsu+"\n학점: "+hakjum);
    }
}
