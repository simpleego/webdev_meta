package DataTypeOperation;

public class Expression01 {
    public static void main(String[] args) {
        int x = 10;
        int y = 10;

        System.out.println("x:"+x);
        System.out.println("x:"+y);

        int nextX = ++x;
        int nextY = ++y;

        System.out.println("x:"+x);
        System.out.println("x:"+y);

        System.out.println("nextX:"+nextX);
        System.out.println("nextY:"+nextY);
    }
}
