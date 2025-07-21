package classex;

public class Calculator {

    public int add(int x, int y) {
        return x + y;
    }

    public int add(int x, int y, int z) {
        return x + y + z;
    }

    public double add(double x, double y) {
        return x + y;
    }

    public double add(double x, int y) {
        return x + y;
    }

    public double add(int x, double y) {
        return x + y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public int divide(int x, int y) {
        if (y != 0) {
            return x / y;
        }
        System.out.println("0으로 나눌 수 없습니다.");
        return 0;
    }

    public int multiply(int x, int y) {

        int sum = 0;
        for (int i = 0; i < y; i++) {
            sum += x;
        }
        return sum;
        //return x * y;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int result = calculator.add(10, 20);
        System.out.println(result);

        double res = calculator.add(10.4, 2.5);
        System.out.println(res);

        result = calculator.divide(10, 0);
        System.out.println(result);

        result = calculator.multiply(2, 3);
        System.out.println(result);

    }
}
