package inheritance;

public class Animal {
    private double weight;
    String picture;

    public Animal() {
        System.out.println("동물이 생성된다.");
    }

    public void eat(){
        System.out.println("먹는다.");
    }
    public void sleep(){
        System.out.println("잠잔다.");
    }
}
