package event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventTest extends JFrame implements KeyListener {
    private JPanel panel;
    private JTextField field;
    private JTextArea area;

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Typed");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Released");
    }

    public KeyEventTest() throws HeadlessException {
        panel = new JPanel(new GridLayout(0,2));
        panel.add(new JLabel("문자를 입력하세요."));
        field = new JTextField(10);
        panel.add(field);
        area = new JTextArea(3,30);
        setVisible(true);

        add(panel,BorderLayout.NORTH);
        add(area,BorderLayout.CENTER);

        field.addKeyListener(this);

        setTitle("키이벤트 처리");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,200);
    }

    public static void main(String[] args) {
        new KeyEventTest();
    }
}
