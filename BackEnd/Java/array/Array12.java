package array;

public class Array12 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5};
        int[] numbers;

        for (int num : nums) {
            System.out.print("nums: "+num+" ");
        }
        System.out.println();

        // 배열 주소를 넘겨주고, 배열 값을 같이 공유
        numbers = nums;

        for (int num : numbers) {
            System.out.print("numbers: "+num+" ");
        }
        System.out.println();

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] += 20;
        }
        // 배열 공유 확인
        for (int num : numbers) {
            System.out.print("numbers: "+num+" ");
        }

        System.out.println();
        for (int num : nums) {
            System.out.print("nums: "+num+" ");
        }

    }

}
