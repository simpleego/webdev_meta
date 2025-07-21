package frame01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calc01 extends JFrame {
    JTextField jTextField1;
    JTextField jTextField2;
    JTextField outTextField;
    JButton addButton;
    JButton subButton;
    JButton divButton;
    JButton mulButton;

    public Calc01() {
        // 윈도우 초기설정
        setSize(300,200);
        setTitle(">> 사칙연산기 <<");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());

        // 컴포넌트 생성(무대설비, 배우, 등을 섭외했음)
        jTextField1 = new JTextField(30);
        jTextField2 = new JTextField(30);
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("/");
        outTextField = new JTextField(30);

        //addButton.addActionListener(this);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 두개의 숫자를 가져와서 덧셈
                String num1_ = jTextField1.getText();
                String num2_ = jTextField2.getText();
                int result = Integer.parseInt(num1_)+
                        Integer.parseInt(num2_);
                outTextField.setText(result+"");
            }
        });

        subButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 두개의 숫자를 가져와서 덧셈
                String num1_ = jTextField1.getText();
                String num2_ = jTextField2.getText();
                int result = Integer.parseInt(num1_)-
                        Integer.parseInt(num2_);
                outTextField.setText(result+"");
            }
        });

        mulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 두개의 숫자를 가져와서 덧셈
                String num1_ = jTextField1.getText();
                String num2_ = jTextField2.getText();
                int result = Integer.parseInt(num1_)*
                        Integer.parseInt(num2_);
                outTextField.setText(result+"");
            }
        });

        divButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 두개의 숫자를 가져와서 덧셈
                double result = 0.0;
                String num1_ = jTextField1.getText();
                String num2_ = jTextField2.getText();
                if(Integer.parseInt(num2_) == 0) {
                    outTextField.setText("0으로 나눌 수 없습니다.");
                    return;
                }

                if(Integer.parseInt(num2_) != 0){
                    result = Integer.parseInt(num1_)/
                        Double.parseDouble(num2_);
                }
                outTextField.setText(String.format("%6.2f",result));
            }
        });

        // 컴포넌트를 컨테이너에 등록
        this.add(jTextField1);
        this.add(jTextField2);
        this.add(addButton);
        this.add(subButton);
        this.add(mulButton);
        this.add(divButton);
        this.add(outTextField);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Calc01();
    }
}
