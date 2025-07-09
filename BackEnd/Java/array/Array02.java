package array;

import java.util.Scanner;

public class Array02 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        int s[] = new int[10];
        int sum = 0;
        double avg;

        for (int i = 0; i < s.length; i++) {
            s[i] = (int) (Math.random() * 10 + 1);  // 1~10 사이의 난수를 생성하여 처리
        }

        for (int i = 0; i < s.length; i++) {
            System.out.print(s[i] + ",");
        }

        for (int i = 0; i < s.length; i++) {
                sum += s[i];
        }

        avg = (double) sum/s.length;
        System.out.println();
        System.out.println("총합 : "+sum);
        System.out.println("평균 : "+avg);
    }
}
