import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 
public class ScanTest {
	public static void main(String[] args) throws IOException {

		Scanner s = null;

		try {
			s = new Scanner(new BufferedReader(new FileReader("input.txt")));

			while (s.hasNext()) {
				System.out.println(s.next());
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}
}