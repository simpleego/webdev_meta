package array;

import java.util.Arrays;

public class Array14 {
    public static void main(String[] args) {
        final int SIZE = 10;
        int[] numbers = new int[SIZE];

        for (int i = 0; i <numbers.length; i++) {
            numbers[i] = (int)(Math.random()*100);
        }

        System.out.println("정렬하기 전의 배열 값");
        for (int num : numbers){
            System.out.print(num+",");
        }
        System.out.println();

        Arrays.sort(numbers);

        System.out.println("정렬한 후의 배열 값");
        for (int num : numbers){
            System.out.print(num+",");
        }
    }
}
