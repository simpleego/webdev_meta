class Shape {
	protected int x, y;
}

class Rectangle extends Shape {
	private int width, height;
}

class Triangle extends Shape {
	private int base, height;
}

class Circle extends Shape {
	private int radius;
}

public class ShapeTest {
	public static void main(String arg[]) {
		Shape s = new Rectangle();
		Rectangle r = new Rectangle();
		s.x = 0;
		s.y = 0;
		// s.width = 100;
		// s.height = 100;
	}
}