import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

public class ByteStreamsLab {
	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.print("원본 파일 이름을 입력하시오: ");

//try 블록 안에서 스트림을 선언하고 초기화하면 이것들을 닫을 필요가 없다. 따른 클래스에 대해서도 사용할 수 있으나 Closable 인터페이스를 구현한 클래스이어야 한다. 
		String inputFileName = scan.next();

		System.out.print("복사 파일 이름을 입력하시오: ");
		String outputFileName = scan.next();

		try (InputStream inputStream = new FileInputStream(inputFileName);
				OutputStream outputStream = new FileOutputStream(outputFileName)) {

//파일의 끝까지  읽기를 계속한다. 
			int c;

			while ((c = inputStream.read()) != -1) {
				outputStream.write(c);
			}
		}
		System.out.println(inputFileName + "을 " + outputFileName + "로 복사하였습니다. ");
	}
}