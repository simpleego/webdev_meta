package interfaceEx;

public class RemoCon implements RemoteControl{
    Television tv;

    public RemoCon(Television tv) {
        this.tv = tv;
    }

    @Override
    public void turnOn() {
        // tv객체가 갖고 있는 turnOn() 메서드 호출
        tv.turnOn();
        //System.out.println("TV를 켠다.");
    }

    @Override
    public void turnOff() {
        tv.turnOff();
        //System.out.println("TV를 끄다.");

    }
}
