import java.util.*; 	// Scanner 클래스를 포함한다.

public class CircleArea {
	public static void main(String args[]) {

		double radius; // 원의 반지름
		double area; // 원의 면적
		Scanner input = new Scanner(System.in);
		System.out.print("반지름을  입력하시오: "); // 입력 안내 출력
		radius = input.nextDouble();
		area = 3.14 * radius * radius;

		System.out.println(area);
	}

}