import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다.
public class TextConverter extends JFrame {
	JButton converter;
	JButton canceler;
	JTextArea textIn;
	JTextArea textOut;

	public TextConverter() {
		super("텍스트 변환");

		// 텍스트 영역
		textIn = new JTextArea(10, 14);
		textOut = new JTextArea(10, 14);
		textIn.setLineWrap(true);// 자동 줄바꿈
		textOut.setLineWrap(true);
		textOut.setEnabled(false);// 비활성화

		// 텍스트 영역을 관리할 패널
		JPanel textAreaPanel = new JPanel(new GridLayout(1, 2, 20, 20));
		textAreaPanel.add(textIn);
		textAreaPanel.add(textOut);

		// 버튼
		converter = new JButton("변환");
		canceler = new JButton("취소");
		converter.addActionListener(new ButtonActionListener());
		canceler.addActionListener(new ButtonActionListener());

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(converter);
		buttonPanel.add(canceler);

		// 메인 패널
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.add(BorderLayout.CENTER, textAreaPanel);
		mainPanel.add(BorderLayout.SOUTH, buttonPanel);

		// 프레임 설정
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		add(mainPanel);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	// 버튼의 액션 이벤트를 처리 할 버튼 액션 리스너 클래스
	private class ButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == converter) {
				textOut.setText("");
				String result = toEnglish(textIn.getText());
				textOut.append(result);
			}
			if (e.getSource() == canceler) {
				textOut.setText("");
			}
		}

		// 영어를 한국어로 변환하는 메소드
		private String toEnglish(String korean) {
			String result = korean;
			result = result.replace("텍스트", "Text");
			result = result.replace("영어", "English");
			return result;
		}
	}

	public static void main(String[] args) {
		TextConverter t = new TextConverter();
	}
}