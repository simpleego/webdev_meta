import java.util.Scanner;

public class NumberGame {
	public static void main(String args[]) {
	       int answer =59;     // 정답
	       int guess;
	       Scanner sc=new Scanner(System.in);
	       int tries = 0;
	       // 반복 구조
	       do {
	             System.out.print("정답을 추측하여 보시오: ");
	             guess = sc.nextInt();
	             tries++;
	             
	             if (guess >answer)  // 사용자가 입력한 정수가 정답보다 높으면
	            	 System.out.println("제시한 정수가 높습니다.");
	             if (guess <answer)  // 사용자가 입력한 정수가 정답보다 낮으면
	            	 System.out.println("제시한 정수가 낮습니다.");
	       } while (guess !=answer);
	       
	       System.out.println("축하합니다. 시도횟수=" + tries);
	}
}