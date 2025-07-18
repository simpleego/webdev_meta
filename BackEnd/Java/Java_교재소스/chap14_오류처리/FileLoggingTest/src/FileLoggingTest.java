import java.io.IOException;

import java.util.logging.*;

public class FileLoggingTest {
	private static final Logger logger = Logger.getLogger(FileLoggingTest.class.getName());

	public static void main(String[] args) throws IOException {

		Handler handler = new FileHandler("logging.txt");

		logger.addHandler(handler);
		logger.setLevel(Level.FINEST);
		logger.info("info 레벨 메시지");

		logger.fine("fine 레벨 메시지");
		logger.finest("finest 레벨 메시지");

		handler.flush();
		handler.close();
	}
}