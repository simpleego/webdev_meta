package inheritance;

public class Manager extends Employee{
    private  int bonus;

    public Manager(String name, String address, int salary, int rrn,
                    int bonus) {
        super(name, address, salary, rrn);
        this.bonus = bonus;
    }

    public static void main(String[] args) {
        Manager manager = new Manager("Tom", "서울", 1100, 122, 100);
    }
}
