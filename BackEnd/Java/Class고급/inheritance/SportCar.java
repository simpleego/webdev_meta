package inheritance;

public class SportCar  extends Car{
    boolean turbo;

    public SportCar() {
        System.out.println("스포츠 카가 생성됩니다.");
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    public static void main(String[] args) {
        SportCar sportCar = new SportCar();
        Car car = new Car();

        sportCar.setSpeed(200);
        sportCar.setTurbo(true);

        car.setSpeed(100);
        System.out.println(sportCar.speed);

    }
}
