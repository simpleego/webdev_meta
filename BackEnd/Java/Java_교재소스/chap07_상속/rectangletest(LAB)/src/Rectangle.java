public class Rectangle extends Shape {
	int width;
	int height;

	double area() {
		return (double)width * height;
	}
	void draw() {
		System.out.println("(" + x + "," + y + ") 위치에 " + "가로: " + width
				+ " 세로: " + height);
	}
}