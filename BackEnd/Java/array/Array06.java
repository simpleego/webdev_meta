package array;

public class Array06 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        int[] num = {12, 3, 19, 6, 18, 12, 4, 1, 19};
        int value, index = -1;
        value = 7;

        for (int i = 0; i < num.length; i++) {
            System.out.printf("%d ", num[i]);
        }

        for (int i = 0; i < num.length; i++) {
            if(num[i] == value){
                index = i;
                break;
            }
        }

        System.out.println("찾고자하는 값:" + value);

        if (index != -1) {
            System.out.println("찾은값:" + num[index]);
        }else {
            System.out.println("찾을 수 없습니다.");
        }

    }
}
