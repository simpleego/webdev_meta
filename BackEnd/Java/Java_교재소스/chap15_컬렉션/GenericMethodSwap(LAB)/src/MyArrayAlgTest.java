public class MyArrayAlgTest {

	public static void main(String[] args) {
		String[] language = { "C++", "C#", "JAVA" };
		MyArrayAlg.swap(language, 1, 2); 
		for(String value : language)
			System.out.println(value);
	}

}