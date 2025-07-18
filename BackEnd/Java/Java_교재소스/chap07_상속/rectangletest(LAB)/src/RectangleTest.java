public class RectangleTest {
	public static void main(String args[]) {
		Rectangle r1 = new Rectangle();
		Rectangle r2 = new Rectangle();

		r1.x = 5;
		r1.y = 3;
		r1.width = 10;
		r1.height = 20;

		r2.x = 8;
		r2.y = 9;
		r2.width = 10;
		r2.height = 20;

		r1.print();
		r1.draw();
		r2.print();
		r2.draw();
	}
}