import java.io.File;
import java.io.IOException;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

//여기서 만약 File 객체를 생성할 때 파일의 이름만 주면 현재 디렉토리에서 파일을 찾는다. 따라서 현재 디렉토리에 있지 않은 파일은 절대 경로로 이름을 주어야 한다. 

public class FileTest {
	public static void main(String[] args) throws IOException {
		String name = "c:/eclipse";
		File dir = new File(name);
		String[] fileNames = dir.list(); // 현재 디렉토리의 전체 파일 리스트
		for (String s : fileNames) {
			File f = new File(name + "/" + s); // 절대 경로로 이름을 주어야 함
			System.out.println("===============================");
			System.out.println("이름: " + f.getName());
			System.out.println("경로: " + f.getPath());
			System.out.println("부모: " + f.getParent());
			System.out.println("절대경로: " + f.getAbsolutePath());
			System.out.println("정규경로: " + f.getCanonicalPath());
			System.out.println("디렉토리 여부:" + f.isDirectory());
			System.out.println("파일 여부:" + f.isFile());
			System.out.println("===============================");
		}
	}
}