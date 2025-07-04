package DataTypeOperation;

public class TypeCasting01 {
    public static void main(String[] args) {
        int a= 5;
        int b = 3;
        double c;

        // 소숫점 값이 나오도록 수정
        c = a/3.0;
        //c = (double) (a / b);

        System.out.println((a/b));
        System.out.println(c);
        //1.6666666666666667
        //  1234567890123456

    }
}
