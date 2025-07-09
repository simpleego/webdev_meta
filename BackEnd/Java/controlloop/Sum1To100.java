package controlloop;

public class Sum1To100 {
    public static void main(String[] args) {
        int sum = 0;

        for(int i=1; i<=10; i++ ){
            //sum = sum + i;
            sum += i;
            System.out.println(i+" : "+sum);
        }

        System.out.println("합계 : "+sum);
    }
}
