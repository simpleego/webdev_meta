package frame01;

import javax.swing.JFrame;
import java.awt.*;

public class WindowFrame01 extends JFrame {
    JFrame jFrame;

    public WindowFrame01(JFrame jFrame) throws HeadlessException {
        this.jFrame = jFrame;
    }

    public WindowFrame01() {
        this.setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("첫번째 윈도우");
        setVisible(true);
    }

    public static void main(String[] args) {
        //new WindowFrame01();
        JFrame jFrame = new JFrame();
        jFrame.setSize(200,300);
        jFrame.setTitle("윈도우 2");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        new WindowFrame01(jFrame);
    }
}
