package DataTypeOperation;

public class Variable02 {
    public static void main(String[] args) {

        // 변수: 데이터값 누적하기
        int m1=10;
        int m2=20;
        int m3=30;
        int total = 0;

        // 년매출 구하기
        total = total + m1;  // 1월까지 매출총액
        System.out.println("1월매출총액:"+total);

        total = total + m2;  // 1월까지 매출총액
        System.out.println("2월매출총액:"+total);

        total = total + m3;  // 1월까지 매출총액
        System.out.println("3월매출총액:"+total);
    }
}
