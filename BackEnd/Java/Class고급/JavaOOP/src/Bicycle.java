public class Bicycle {
    private int cadence = 0;
    private int speed = 0;
    private int gear = 1;

    void changeCadence(int newValue){
        cadence = newValue;
    }
    void changeGear(int newValue){
        gear = newValue;
    }

    void speedUp(int increment){
        speed = increment;
    }
    void speedDown(int decrement){
        speed = decrement;
    }
    void brake(int decrement){
        speed = speed - decrement;
    }

    void printState(String name){
        System.out.println(name+" 기어:"+gear+" 케이던스:"+cadence+" 속도:"+speed);
    }

}
