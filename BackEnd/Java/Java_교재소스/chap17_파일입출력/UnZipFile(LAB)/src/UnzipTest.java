import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

public class UnzipTest {
	public static void main(String[] args) throws Exception {

//FileInputStream과 ZipInputStream을 서로 연결한다. 

		Scanner sc = new Scanner(System.in);
		System.out.println("압축 파일 이름을 입력하시오: ");
		String inname = sc.next();
		System.out.println("원본 파일 이름을 입력하시오: ");
		String outname = sc.next();
		ZipInputStream inStream = new ZipInputStream(
				new FileInputStream(inname));

//단순히 ZIP 스트림에서 읽어서 출력 스트림에 쓴다. 
		OutputStream outStream = new FileOutputStream(outname);

		byte[] buffer = new byte[1024];
		int read;
		ZipEntry entry;
		if ((entry = inStream.getNextEntry()) != null) {
			while ((read = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, read);
			}
		}
		outStream.close();
		inStream.close();
	}
}