import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

public class SocketTest {
	public static void main(String[] args) throws IOException {
		try (Socket s = new Socket("time-c.nist.gov", 13)) {
			InputStream inStream = s.getInputStream();
			Scanner in = new Scanner(inStream);

			while (in.hasNextLine()) {
				String line = in.nextLine();
				System.out.println(line);
			}
		}
	}
}