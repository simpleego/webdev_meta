package DataTypeOperation;

public class String01 {
    public static void main(String[] args) {
        String s1 = "Hello World";
        //String s2 = "I'm a new Java programmer!";
        String s2 = new String("I'm a new Java programmer!");

        int len = s1.length();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(len);
    }
}
