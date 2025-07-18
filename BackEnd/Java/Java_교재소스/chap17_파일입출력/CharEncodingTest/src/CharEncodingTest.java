import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 


public class CharEncodingTest {
	public static void main(String[] args) throws IOException {
		File fileDir = new File("input.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(
			new FileInputStream(fileDir), "UTF8"));

		String str;

		while ((str = in.readLine()) != null) {
			System.out.println(str);
		}
	}
}
