package classAdvance;

public class Car {
    int speed;
    Car(){
        System.out.println("속도:"+speed);
    }

    {
        speed = 100;
    }

    public static void main(String[] args) {
        Car car1 = new Car();
        Car car2 = new Car();
    }
}
