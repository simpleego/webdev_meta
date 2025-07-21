package inheritance;

public class Bus {
    Car car;
    int passanger;

    public int getPassanger(){
        return passanger;
    }

    public Bus(Car car, int passanger) {
        this.car = car;
        this.passanger = passanger;
    }

    public static void main(String[] args) {

        Bus bus = new Bus(new Car(), 20);
        bus.car.setSpeed(50);
        int count = bus.getPassanger();
        System.out.println("승객수:"+count);
        System.out.println("현재 속도:"+bus.car.speed);
    }
}
