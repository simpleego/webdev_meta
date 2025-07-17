package classAdvance;

public class Employee {
    private String name;
    private double salary;

    private static int count;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        count++;
    }

    protected void finalize(){
        count--;
    }

    public static int getCount(){
        return count;
    }

    public static void setCount(int count1){
        count = count1;
    }

    public static void main(String[] args) {
        Employee e1, e2, e3;

        e1 = new Employee("김철수", 3500);
        e2 = new Employee("홍길동", 4500);
        e3 = new Employee("최수종", 5500);

        int count = Employee.getCount();
        System.out.println("직원수 :"+count);
    }
}
