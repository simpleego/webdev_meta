import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

class MyCounter extends JFrame implements ActionListener {
	private JLabel label, label1;
	private JButton button;
	private int count = 0;

	public MyCounter() {
		JPanel panel = new JPanel();
		label = new JLabel("Counter");
		panel.add(label);

		label1 = new JLabel("" + count);
		label1.setFont(new Font("Serif", 	// 레이블에 폰트를 설정한다. 
			Font.BOLD | Font.ITALIC, 100)); 

		panel.add(label1);

		button = new JButton("카운터 증가");
		panel.add(button);
		button.addActionListener(this);

		add(panel);
		setSize(300, 200);
		setTitle("My Counter");

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		count++;
		label1.setText(count + "");
	}
}

public class CounterTest {
	public static void main(String[] args) {
		new MyCounter();
	}
}