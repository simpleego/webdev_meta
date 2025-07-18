import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 
public class RGB2Gray {
	BufferedImage myImage;
	int width;
	int height;

	public RGB2Gray() {
		File ifile = new File("test.jpg");
		try {
			myImage = ImageIO.read(ifile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = myImage.getWidth();
		height = myImage.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color c = new Color(myImage.getRGB(x, y));
				int red = (int) (c.getRed() * 0.299);
				int green = (int) (c.getGreen() * 0.587);
				int blue = (int) (c.getBlue() * 0.114);
				Color gray = new Color(red + green + blue, red + green + blue, red + green + blue);
				myImage.setRGB(x, y, gray.getRGB());
			}
		}
		File ofile = new File("gray.jpg");
		try {
			ImageIO.write(myImage, "jpg", ofile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static public void main(String args[]) throws Exception {
		RGB2Gray obj = new RGB2Gray();
	}
}