package controlloop;

public class For03 {
    public static void main(String[] args) {
        // 별모양 출력하기
        // 사각형모양으로 5줄 10개씩
        int lines = 10;
        int column = 5;

        for (int j = 0; j < lines; j++) {

            System.out.print(j+":");
            for (int i = 0; i < column; i++) {
                System.out.print("★");
            }
            System.out.println();
        }

    }
}
