class Car {
	private int speed;
	//...

}
public class CarTest {
	public static void main(String[] args) {
		Car obj = new Car();
		System.out.println("obj is of type " + obj.getClass().getName());
	}
}