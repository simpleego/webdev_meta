package genericCollection;

class MyArrayAlg {
    public static <T> T getLast(T[] a){
        return a[a.length-1];
    }
}

public class MyArrayTest {
    public static void main(String[] args) {
        String[] language = {"C++","Java","Python", "C#"};
        Integer[] num = {10,20,30,40};
        String last = MyArrayAlg.getLast(language);
        Integer num1 = MyArrayAlg.getLast(num);
        System.out.println(last);
        System.out.println(num1);
    }
}
