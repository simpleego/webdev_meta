import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

//예제: 직접 입력하여 확인!
public class CopyLines {
    public static void main(String[] args) throws IOException {


//FileReader에 BufferedReader를 연결한다. 
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader("input.txt"));
            outputStream = new PrintWriter(new FileWriter("output.txt"));

//FileWriter의 출력이 PrinterWriter의 입력이 된다. 

            String l;
            while ((l = inputStream.readLine()) != null) {
                outputStream.println(l);
            }

//한줄 단위로 입출력할 수 있다. 
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}