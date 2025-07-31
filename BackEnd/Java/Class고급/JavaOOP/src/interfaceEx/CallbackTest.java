package interfaceEx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

class MyClass implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("beep");
    }
}

public class CallbackTest {
    public static void main(String[] args) {
        ActionListener listener = new MyClass();

        Timer timer = new Timer(10,listener);
        timer.start();
        for (int i = 0; i <100; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // ~~~
        }
    }
}
