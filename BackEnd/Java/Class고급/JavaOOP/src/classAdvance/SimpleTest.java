package classAdvance;

public class SimpleTest {
    public static void main(String[] args) {
        Simple simple = new Simple();
        // System.out.println(simple.age);

        System.out.println(simple.getAge());
        simple.setAge(20);

        simple.num = 100;  // default->package 접근권한
        simple.address = "대전시 서구 ...";


    }
}
