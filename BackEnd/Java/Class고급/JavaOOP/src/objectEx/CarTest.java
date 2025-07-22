package objectEx;

import java.util.Objects;

class Car{
    int speed;
    private final String model;

    public Car(String model) {
        this.model = model;
    }

    public boolean equalsCar(Object obj) {
        //if (obj == null || getClass() != obj.getClass()) return false;
        if(obj instanceof Car){
            return model.equals( ((Car)obj).model );
        }else {
            return false;
        }
        // Car car = (Car) obj;
    }


}

public class CarTest {
    public static void main(String[] args) {
        Car car3;
        Car car1 = new Car("K5");
        Car car2 = new Car("K5");
        car3 = car1;

        String name = car1.getClass().getName();
        System.out.println("너의 부모는: "+name);

        System.out.println(car1.hashCode());
        System.out.println(car3.hashCode());
        System.out.println(car2.hashCode());

        if(car1.equals(car3)){
            System.out.println("동일한 차");
        }else {
            System.out.println("동일하지 않은 차");
        }

        String str = "hello";
        String msg = "hello";

        System.out.println("str:"+str.hashCode());
        System.out.println("msg:"+msg.hashCode());

        if(str.equals(msg)){
            System.out.println("동일한 문자열");
        }else {
            System.out.println("동일하지 않은 문자열");
        }

        msg = "안녕";
        System.out.println(str);
        System.out.println(msg);
        System.out.println(str.hashCode());
        System.out.println(msg.hashCode());

    }
}
