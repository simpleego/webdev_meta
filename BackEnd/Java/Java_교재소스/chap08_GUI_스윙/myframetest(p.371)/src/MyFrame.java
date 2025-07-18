import javax.swing.*;
import java.awt.FlowLayout;
 
public class MyFrame extends JFrame {
       public MyFrame() {
             setSize(300, 200);
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             setTitle("MyFrame");

             setLayout(new FlowLayout());
              JButton button = new JButton("¹öÆ°");
              this.add(button);
             setVisible(true);

       }
}