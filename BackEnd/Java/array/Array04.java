package array;

public class Array04 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        String [] toppings = {"양파","버섯","치즈","고구마"};
        int sum = 0;
        double avg;

        for (int i = 0; i < toppings.length; i++) {
            System.out.printf(" --> %s %s ",toppings[i]);
        }
    }
}
