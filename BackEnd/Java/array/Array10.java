package array;

public class Array10 {
    public static void main(String[] args) {
        System.out.println("숫자들의 합 : " +
               sum(new int[] {1,2,3,4,5}));
    }

    private static int sum(int[] number) {
        int sum=0;
        for (int i = 0; i < number.length; i++) {
            sum += number[i];
        }
        return sum;
    }
}
