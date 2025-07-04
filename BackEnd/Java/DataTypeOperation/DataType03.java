package DataTypeOperation;

public class DataType03 {
    public static void main(String[] args) {
        byte num=127;
        byte num_max = 123; //Long.MAX_VALUE;
        byte one = 1;
        //int numMax = num + one;
        byte numMax = (byte)(num + one);
        System.out.println("num: "+(num+2));
        System.out.println("num: "+num_max);
    }
}
