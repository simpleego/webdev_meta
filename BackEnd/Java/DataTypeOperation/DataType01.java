package DataTypeOperation;

public class DataType01 {
    public static void main(String[] args) {
        long lightSpeed;
        long distance;

        lightSpeed = 300000;

        distance= lightSpeed * 365 * 24 * 60 * 60;

        System.out.println("빛이 1년동안 가는 거리 :"+distance
        + "km");
    }
}
