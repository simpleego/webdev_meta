package classAdvance;

public class MyCar {
    private String model;
    private String color;
    private int speed;

    private int id;
    private static int numbers = 1;

    public MyCar(String model, String color, int speed) {
        this.model = model;
        this.color = color;
        this.speed = speed;

        id = numbers++;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "MyCar{" +
                "model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", speed=" + speed +
                ", id=" + id +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(MyCar.numbers);

        MyCar car1 = new MyCar("K5", "white", 20);
        System.out.println(car1);

        MyCar car2 = new MyCar("K8", "red", 20);
        System.out.println(car2);

        MyCar car3 = new MyCar("K7", "gray", 20);
        System.out.println(car3);

        System.out.println(MyCar.numbers);
        System.out.println(car1.numbers=10);
        System.out.println(car2.numbers);
        System.out.println(car3.numbers);

        MyCar car4 = new MyCar("K3", "white", 20);
        System.out.println(car4);
    }

}
