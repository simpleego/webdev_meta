package exception;

public class DebugTest {
    public static void main(String[] args) {
        int i = 10;
        int[] numbers = {2,5,7,8,9};
        int count=0;
        for (int num : numbers){
            System.out.println(++count+": "+num);

            if(num % 2 == 0 ){
                System.out.println("짝수입니다.");
            }else {
                System.out.println("홀수입니다.");
            }
        }
    }
}
