package array;

public class Array01 {
    public static void main(String[] args) {
        // 정수 10개를 저장할 배열을 준비
        int s[]  = new int[10];

        for (int i = 0; i < s.length; i++) {
            s[i] = i+1;
        }

        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]+",");
        }
    }
}
