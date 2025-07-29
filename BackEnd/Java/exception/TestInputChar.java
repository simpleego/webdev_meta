package exception;

import java.io.IOException;

public class TestInputChar {
    public static void main(String[] args)  {
        //TestInputChar test = new TestInputChar();
        try {
            System.out.println(readString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static String readString() throws IOException {
        byte[] buf = new byte[10];
        System.out.println("문자열 입력하세요");
        System.in.read(buf);
        return new String(buf);
    }
}
