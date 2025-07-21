package inheritance;

public class AnimalTest {
    public static void main(String[] args) {
        Lion lion = new Lion();
        lion.eat();
        lion.sleep();
        lion.picture= "사자";

        String shape = lion.getPicture();
        System.out.println("사자의 모양은 "+shape);
    }
}
