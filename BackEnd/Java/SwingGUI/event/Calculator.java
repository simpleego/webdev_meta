package event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField jTextField1;
    private JTextField jTextField2;
    JTextField outTextField;
    JButton addButton;
    JButton subButton;
    JButton divButton;
    JButton mulButton;

    public Calculator() {
        setTitle("이벤트 처리 방법");
        setSize(200, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        // 컴포넌트 생성 및 등록
        jTextField1 = new JTextField(5);
        jTextField2 = new JTextField(5);
        outTextField = new JTextField(5);
        divButton = new JButton("나눗셈");
        mulButton = new JButton("곱셈");
        add(jTextField1);
        add(jTextField2);
        add(divButton);
        add(mulButton);
        add(outTextField);

        // 컴포넌트 이벤트 등록 및 처리
        divButton.addActionListener(this);//new Divisor());
        mulButton.addActionListener(this);

        setVisible(true);
    }

    public JTextField getjTextField1() {
        return jTextField1;
    }

    public JTextField getjTextField2() {
        return jTextField2;
    }

    public static void main(String[] args) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = 0;
        double result1=0.0;

        if (e.getSource() == mulButton) {
            result = Integer.parseInt(jTextField1.getText()) *
                    Integer.parseInt(jTextField2.getText());
            outTextField.setText(result+"");

        }else if(e.getSource() == divButton){
            if (Integer.parseInt(jTextField2.getText()) != 0) {
                result1 = (double) Integer.parseInt(jTextField1.getText()) /
                        Integer.parseInt(jTextField2.getText());

                outTextField.setText(String.format("%6.2f", result1));
            }
        }

    }

    // 내부 클래스 여기에
    private class Divisor implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(jTextField2.getText()) != 0) {
                double result = (double) Integer.parseInt(jTextField1.getText()) /
                        Integer.parseInt(jTextField2.getText());

                outTextField.setText(String.format("%6.2f", result));
            }
        }
    }
}
