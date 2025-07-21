package classAdvance;

public class Dog extends Animal{

    public void sleep() {
        super.sleep();
    }

    public void sound(){
        System.out.println(" 개는 멍멍하고 짓는다");
    }

    public void speak(){
        System.out.println(" 개가 짓는다");
    }

    public static void main(String[] args) {
        Animal animal = new Animal();
        Dog dog = new Dog();
        Animal animalDog = new Dog();

        animal.sound();
        dog.sound();
        animalDog.sound();

        animalDog.sleep();
        animalDog.sound();

        dog.sleep();


    }
}
