package controlloop;

public class For04 {
    public static void main(String[] args) {
        // 별모양 출력하기
        // 사각형모양으로 5줄 10개씩
        int lines = 10;
        int column = 5;
        int num = 0;

        for (int j = 0; j < lines; j++) {

            System.out.print(j+":");
            for (int i = 0; i < column; i++) {
                System.out.printf("%5d",++num);
            }
            System.out.println();
        }

    }
}
