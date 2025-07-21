package inheritance;

public class Eagle extends Animal{
    private int wings=2;

    public Eagle() {
        System.out.println("독수리가 생성됩니다.");
    }

    public void fly(){
        System.out.println("날개짓을 하면서 날다.");
    }
}
