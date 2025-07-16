package classAdvance;

import java.util.Scanner;

public class Sungjuk {
    // 성적을 입력받고 성적의 평균을 구한다.
    int sungjuks[] = new int[5];
    Scanner kbd = new Scanner(System.in);

    public void getSungjuk() {
        for (int i = 0; i < sungjuks.length; i++) {
            System.out.println("성적입력 후 Enter");
            sungjuks[i] = kbd.nextInt();
        }
    }

    public double getAvg(int[] jumsu) {
        int sum = 0;
        for (int j : jumsu) {
            sum += j;
        }
        double avg =  (double) sum / jumsu.length;
        return avg;
    }

    public static void main(String[] args) {
        Sungjuk sungjuk = new Sungjuk();
        sungjuk.getSungjuk();
        double avg = sungjuk.getAvg(sungjuk.sungjuks);
        System.out.println("평균 : "+ avg);
    }

}
