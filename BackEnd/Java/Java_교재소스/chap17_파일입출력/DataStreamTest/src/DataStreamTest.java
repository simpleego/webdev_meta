import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

public class DataStreamTest {
	public static void main(String[] args) throws IOException {
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			int c;

			out = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream("data.bin")));
			out.writeDouble(3.14);

//DataOutputStream은 기존의 바이트 스트림 객체의 랩퍼 클래스로만 생성될 수 있기 때문에  먼저 버퍼된 파일 출력 바이트 스트림을 생성하고 이것을 생성자의 매개 변수로 전달한다. 

			out.writeInt(100);
			out.writeUTF("자신의 생각을 바꾸지 못하는 사람은 결코 현실을 바꿀 수 없다.");

			out.flush();
			in = new DataInputStream(new BufferedInputStream(

//writeUTF() 메소드는 문자열을 UTF-8의 변형된 형식으로 출력한다. UTF-8은 일반적인 영문자를 하나의 바이트로 표현하는 가변 길이 문자 엔코딩이다. 
					new FileInputStream("data.bin")));

			System.out.println(in.readDouble());
			System.out.println(in.readInt());
			System.out.println(in.readUTF());

		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}