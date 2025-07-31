package interfaceEx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    int num1;
    int num2;
    double result;

    JTextField tfNum1;
    JTextField tfNum2;
    JTextField tfResult;
    JButton btnAdd;
    JButton btnSub;
    JButton btnMul;
    JButton btnDiv;


    public Calculator() throws HeadlessException {
        setTitle("calc");
        setSize(100,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        tfNum1 = new JTextField(5);
        tfNum2 = new JTextField(5);
        tfResult = new JTextField(5);
        btnAdd = new JButton("+");
        btnSub = new JButton("-");
        btnMul = new JButton("x");
        btnDiv = new JButton("/");

        btnAdd.addActionListener(this);
        btnMul.addActionListener(this);

        btnDiv.addActionListener( (e)-> {
                num1 = Integer.parseInt(tfNum1.getText());
                num2 = Integer.parseInt(tfNum2.getText());
                result = num1/num2;
                tfResult.setText(result+"");
        });


        btnSub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num1 = Integer.parseInt(tfNum1.getText());
                num2 = Integer.parseInt(tfNum2.getText());
                result = num1-num2;
                tfResult.setText(result+"");
            }
        });

        add(tfNum1);
        add(tfNum2);
        add(tfResult);
        add(btnAdd);
        add(btnSub);
        add(btnMul);
        add(btnDiv);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        num1 = Integer.parseInt(tfNum1.getText());
        num2 = Integer.parseInt(tfNum2.getText());

        String op = e.getActionCommand();

        System.out.println(e.getActionCommand());

        //System.out.println("mul:"+btnMul.getText());
        //System.out.println("add:"+btnAdd.getText());

        if(op.equals("x")){
            result = num1*num2;
        }else if(op.equals("+")){
            result = num1+num2;
        }

        tfResult.setText(result+"");
        System.out.println("clicked");
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
