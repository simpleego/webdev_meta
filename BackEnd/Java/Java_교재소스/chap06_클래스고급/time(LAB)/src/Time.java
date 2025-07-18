public class Time {
	private int hour; // 0 - 23
	private int minute; // 0 - 59
	private int second; // 0 - 59

	// 첫 번째 생성자
	public Time() {
		this(0, 0, 0);
	}

	// 두 번째 생성자
	public Time(int h, int m, int s) {
		hour = ((h >= 0 && h < 24) ? h : 0); // 시간 검증
		minute = ((m >= 0 && m < 60) ? m : 0); // 분 검증
		second = ((s >= 0 && s < 60) ? s : 0); // 초 검증
	}

	// “시:분:초”의 형식으로 출력 
	public String toString() {
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
} 