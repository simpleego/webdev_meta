package array;

import java.util.Arrays;

public class Array13 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5};
        int[] numbers;

        for (int num : nums) {
            System.out.print("nums: "+num+" ");
        }
        System.out.println();

        // 배열값을 복사해서 넘겨주고, 배열 값을 개별적으로 사용
        numbers = Arrays.copyOf(nums,nums.length);  // nums;

        for (int num : numbers) {
            System.out.print("numbers: "+num+" ");
        }
        System.out.println();

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] += 20;
        }
        // 각각의 배열 확인
        for (int num : numbers) {
            System.out.print("numbers: "+num+" ");
        }
        
        System.out.println();
        for (int num : nums) {
            System.out.print("nums: "+num+" ");
        }

    }

}
