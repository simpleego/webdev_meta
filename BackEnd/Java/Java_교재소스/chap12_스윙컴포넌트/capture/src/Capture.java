import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

public class Capture {
	public static void main(String[] args) {
		JFrame capture = new JFrame();
		capture.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension d;
		Rectangle rect = new Rectangle(500, 500);
		capture.setSize(d = new Dimension(500, 500));

		try {
			Robot robot = new Robot();
			final BufferedImage image = robot.createScreenCapture(rect);
			image.flush();
			JPanel panel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(image, 0, 0, d.width, d.height, this);
				}
			};
			panel.setOpaque(false);
			panel.prepareImage(image, panel);
			panel.repaint();
			capture.getContentPane().add(panel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		capture.setVisible(true);
	}
}