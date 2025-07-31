package genericCollection;

import java.util.HashMap;
import java.util.Map;

class Student{
    int number;
    String name;

    public Student(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }
}

public class MapTest {
    public static void main(String[] args) {

        Map<String, Student> st = new HashMap<>();
        st.put("20090001",new Student(20090001,"구준표"));
        st.put("20090002",new Student(20090002,"금잔디"));
        st.put("20090003",new Student(20090003,"윤지후"));
        st.put("20090004",new Student(20090004,"차인표"));

        // 모든 항목 출력한다.
        System.out.println(st.size());

        // 항목 삭제
        st.remove("20090001");

        System.out.println(st.size());
        st.put("20090005",new Student(20090005,"소이정"));
        System.out.println(st);
        System.out.println(st.size());
        System.out.println("---------------------------------------");
        st.put("20090010",new Student(20090002,"금잔디"));
        System.out.println(st);
        System.out.println(st.size());

        st.put("20090010",new Student(20090010,"금잔디"));
        System.out.println(st);
        System.out.println(st.size());

        for( Map.Entry<String,Student> s: st.entrySet()){
            String key = s.getKey();
            Student value = s.getValue();
            System.out.println("key = "+key+"value= "+value.name);
        }

    }
}
