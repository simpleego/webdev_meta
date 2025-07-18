
public class SwitchTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int number=2;
		switch(number) 
		{
			case  0:
				System.out.println("없음");
				break;
			case  1:
				System.out.println("하나");
				break;
			case  2:
			case  3:
				System.out.println("두서너 개");
				break;
			default:
				System.out.println("많음");
				break;
		}
	}

}
