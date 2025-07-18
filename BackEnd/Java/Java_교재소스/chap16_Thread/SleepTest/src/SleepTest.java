public class SleepTest {
	public static void main(String args[]) throws InterruptedException {
		String messages[] = { "Pride will have a fall.",
		"Power is dangerous unless you have humility.",
		"Office changes manners.", 
		"Empty vessels make the most sound." };

		for (int i = 0; i < messages.length; i++) {
			Thread.sleep(1000);

//1000밀리초동안 실행을 중지한다. 
			System.out.println(messages[i]);
		}
	}
}