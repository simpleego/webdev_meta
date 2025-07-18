package textareaframe;
// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextAreaFrame extends JFrame implements ActionListener {
       protected JTextField textField;
       protected JTextArea textArea;
 
       public TextAreaFrame() {
             setTitle("Text Area Test");
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
             textField = new JTextField(30);
             textField.addActionListener(this);
 
             textArea = new JTextArea(10, 30);
             textArea.setEditable(false);

 
             add(textField, BorderLayout.NORTH);
             add(textArea, BorderLayout.CENTER);
 
             pack();
             setVisible(true);
       }
 

       public void actionPerformed(ActionEvent evt) {
             String text = textField.getText();
             textArea.append(text + "\n");
             textField.selectAll();
             textArea.setCaretPosition(textArea.getDocument().getLength());
       }

       public static void main(String[] args) {
             new TextAreaFrame();
       }
}