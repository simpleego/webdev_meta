import java.util.Scanner;

public class AssertionTest {

	public static void main(String argv[]) {
		Scanner input = new Scanner(System.in);
		System.out.print("날짜를 입력하시오: ");
		int date = input.nextInt();

		// 날짜가 1 이상이고 31 이하인지를 검증한다.
		assert(date >= 1 && date <= 31) : "잘못된 날짜: " + date;
		System.out.printf("입력된 날짜는 %d입니다.\n", date);
	}
}