import java.util.Scanner;

public class Grading {
	public static void main(String[] args) {
		int score;

		Scanner input = new Scanner(System.in);
		System.out.print("성적을 입력하시오: ");
		score = input.nextInt();
		if (score >= 90)			// ①
			System.out.println("학점 A");
		else if (score >= 80)		// ②
			System.out.println("학점 B");
		else if (score >= 70)
			System.out.println("학점 C");
		else if (score >= 60)
			System.out.println("학점 D");
		else
			System.out.println("학점 F");

	}
}