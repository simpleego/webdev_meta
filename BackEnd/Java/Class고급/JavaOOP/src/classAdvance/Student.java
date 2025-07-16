package classAdvance;

public class Student {
    private int number;
    private String name;
    private int age;

    public Student() {
        number = 1;
        name = "홍길동";
        age = 10;
    }

    public Student(int number) {
        this.number = number;
        name = "홍길동";
        age = 20;
    }

    public Student(int number, String name, int age) {
        this.number = number;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(student);

        Student student1 = new Student(2000);
        System.out.println(student1);

        Student student2 = new Student(10,"박찬호",46);
        System.out.println(student2);

    }

}
