package randomnumber;
import java.util.Random;
import java.util.Scanner;

public class RandomNumber {
	public static void main(String args[]) {

		Random generator = new Random();

		System.out.print("난수의 개수: ");
		Scanner scan = new Scanner(System.in);
		int count = scan.nextInt();
		int sum = 0;

		for (int i = 0; i < count; i++) {
			int number = generator.nextInt(100);
			sum += number;
		}
		System.out.print("난수 " + count + "개의 합은 " + sum);
	}
}