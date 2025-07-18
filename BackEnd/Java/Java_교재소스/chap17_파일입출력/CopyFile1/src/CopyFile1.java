import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 
public class CopyFile1 {
    public static void main(String[] args) throws IOException {

//input.txt 파일에 연결된 파일 입력 스트림을 생성한다. 

        FileInputStream in = null;
        FileOutputStream out = null;


//output.txt 파일에 연결된 파일 출력 스트림을 생성한다. 
        try {
            in = new FileInputStream("input.txt");
            out = new FileOutputStream("output.txt");

//하나의 바이트를 읽을 때는 read()를 사용하고 하나의 바이트를 쓸 때는 write()
//를 사용한다. 
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) 
                in.close();
            if (out != null) 
                out.close();
        }
    }
}