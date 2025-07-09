package controlloop;

public class While02 {
    public static void main(String[] args) {
        // 0부터 9까지의 숫자를 출력하시오
        // 0,1,2,3,4,5,6,7,8,9
        int i = 0;
        int j = 0;

        while (i<10){
            System.out.print((j++) +",");
            i++;
        }
        System.out.println("\n프로그램 종료..");
    }
}
