public class Television implements RemoteControl {
	boolean onOff = false;
	public void turnOn() {
		// TV의 전원을 켜기 위한 코드가 들어간다.
		onOff = true;
	}

	public void turnOff() {
		// TV의 전원을 끄기 위한 코드가 들어간다.
		onOff = false;
	}
}