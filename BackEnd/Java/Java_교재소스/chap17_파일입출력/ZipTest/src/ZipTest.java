import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 
public class ZipTest {

	public static void main(String[] args) throws IOException {

		FileInputStream fin = new FileInputStream("test.zip");
		ZipInputStream zin = new ZipInputStream(fin);
		ZipEntry entry = null;
		while ((entry = zin.getNextEntry()) != null) {
			System.out.println("압축 해제: " + entry.getName());
			FileOutputStream fout = new FileOutputStream(entry.getName());
			for (int c = zin.read(); c != -1; c = zin.read()) {
				fout.write(c);
			}
			zin.closeEntry();
			fout.close();
		}
		zin.close();
	}
}