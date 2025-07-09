package array;

public class Array07 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        int[] num = {12, 3, 19, 6, 18, 12, 4, 1, 19, 12};
        int value;
        int count = 0;
        int[] searchedIndex = new int[num.length];

        value = 19;

        for (int i = 0; i < searchedIndex.length; i++) {
            System.out.println(searchedIndex[i] + ",");
        }

        for (int i = 0; i < num.length; i++) {
            System.out.printf("%d ", num[i]);
        }

        for (int i = 0; i < num.length; i++) {
            if (num[i] == value) {
                searchedIndex[count++] =i;
            }
        }

        System.out.println("찾고자하는 값:" + value);

        if (count > 0) {
            System.out.println("찾은값:" + value);

            for (int i = 0; i <count ; i++) {
                System.out.println("찾은 위치:" + searchedIndex[i]+",");
            }

        } else {
            System.out.println("찾을 수 없습니다.");
        }

    }
}
