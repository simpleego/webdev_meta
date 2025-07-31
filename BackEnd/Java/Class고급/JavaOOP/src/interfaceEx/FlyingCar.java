package interfaceEx;

interface Drivable{
    void drive();
}

interface Flyable{
    void fly();
}

public class FlyingCar implements Flyable,Drivable{
    @Override
    public void drive() {
        System.out.println("도로를 주행할수있다.");
    }

    @Override
    public void fly() {
        System.out.println("하늘을 날 수 있다.");
    }

    public static void main(String[] args) {
        new FlyingCar();

    }
}
