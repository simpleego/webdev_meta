package array;

public class Array03 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        int [] scores = {10,20,30,45,78,55,90};
        int sum = 0;
        double avg;

        for (int i = 0; i < scores.length; i++) {
            System.out.print(scores[i] + ",");
        }
    }
}
