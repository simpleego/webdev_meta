package array;

public class Array15 {
    public static void main(String[] args) {
        int sum=0;
        double avg;

        int[][] sungjuk = {
                {19, 20, 90},
                {90, 85, 95},
                {78, 88, 85},
        };

        for (int i = 0; i < 3; i++) {
                 sum += sungjuk[i][0];
        }
        avg = (double) sum / 3;

        System.out.println("총점:"+sum);
        System.out.printf("평균: %f",avg);
    }
}
