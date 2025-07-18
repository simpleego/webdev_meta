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
		Shape s1, s2;

		s1 = new Shape();		//  ① 당연하다.  
		s2 = new Rectangle();	//  ② Rectangle 객체를 Shape 변수로 가리킬 수 있을까?
	}
}