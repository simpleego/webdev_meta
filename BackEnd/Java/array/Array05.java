package array;

public class Array05 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        int [] num = {12,3,19,6,18,12,4,1,19};
        int min;
        int max;

        for (int i = 0; i < num.length; i++) {
            System.out.printf("%d ",num[i]);
        }

        max = num[0];
        min = num[0];
        for (int i = 1; i < num.length; i++) {
            if(num[i] > max){
                max = num[i];
            }
            if(num[i] < min){
                min = num[i];
            }
        }
        System.out.println("최대값: "+max);
        System.out.println("최소값: "+min);
    }
}
