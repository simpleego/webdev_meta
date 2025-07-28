package event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DivProcessor implements ActionListener {
    Calculator calculator;

    public DivProcessor(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 2개의 숫자?
        String num1_ = calculator.getjTextField1().getText();
        String num2_ = calculator.getjTextField2().getText();
        int num1 = Integer.parseInt(num1_);
        int num2 = Integer.parseInt(num2_);
        double result = 0.0;

        if(num2 != 0){
            result = (double) num1 / num2;
            calculator.outTextField.setText(String.format("%6.2f",result));
            return;
        }

        JOptionPane.showMessageDialog(null, "0으로 나눌 수 없습니다.");
        calculator.outTextField.setText(String.valueOf(result));
    }
}
