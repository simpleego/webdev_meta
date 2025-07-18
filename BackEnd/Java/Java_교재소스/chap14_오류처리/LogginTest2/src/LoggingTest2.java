import java.util.logging.*;

public class LoggingTest2 {
	private static final Logger logger = Logger.getLogger("kr.co.compant.app");

	public static void main(String[] args) {
		logger.info("로깅이 시작됩니다...");
		try {
			throw new Exception("고의적으로 예외를 발생시킨다.");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		logger.info("완료되었음...");
	}
}
