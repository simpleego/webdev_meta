import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@FunctionalInterface
interface MyInterface {
	public void calculate(int x, int y);
}

public class LambdaTest3 {

	public static void main(String[] args) {

		List<String> list = new ArrayList();
		list.add("AAA");
		list.add("bbb");
		list.add("CCC");
		list.add("ddd");
		list.add("EEE");

		// 대소문자를 구별하는 정렬
		Collections.sort(list);

		System.out.println("대소문자를 구별한 정렬:");
		System.out.println(list);

		// 람다식을 이용하여서 대소문자를 무시하는 비교 메소드를 작성하여 전달한다. 
		Collections.sort(list, (o1, o2) -> o1.compareToIgnoreCase(o2));

		System.out.println("대소문자를 구별하지 않은 정렬:");
		System.out.println(list);
	}

}