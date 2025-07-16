import java.util.Scanner;

public class User {
    int id = 0;
    String name = "홍길동";

    public static void main(String[] args) {

        Scanner kbd = new Scanner(System.in);
        Bicycle myBicycle = new Bicycle();
        Bicycle myBicycle1 = new Bicycle();

        myBicycle.printState("my1");
        myBicycle1.printState("my2");

        //myBicycle.speed = 20;
        myBicycle.speedUp(30);
        myBicycle1.speedUp(60);
        myBicycle.printState("my1");
        myBicycle1.printState("my2");
        myBicycle.speedUp(10);
        myBicycle1.speedUp(20);
        myBicycle.printState("my1");
        myBicycle1.printState("my2");
        //System.out.println("속도:"+myBicycle.speed);
    }
}
