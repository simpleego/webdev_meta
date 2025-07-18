package snippet;
interface OperateCar {
	void start();
	void stop();
	void setSpeed(int speed);
	void turn(int degree);

	default public void fly() {
		System.out.println("하늘을 나르고 있음!");
	}
}

class OldCar implements OperateCar {
	public void start() {	}
	public void stop() {	}
	public void setSpeed(int speed) {	}

	public void turn(int degree) {	}
}

public class DefaultMethodTest2 {
	public static void main(String[] args) {
		OldCar obj = new OldCar();
		obj.fly();
	}
}