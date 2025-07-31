package interfaceEx;

public class Television implements RemoteControl {
    private int channel;
    private int volume;

    public void volumeUp(){
        volume++;
    }

    public  void setChannel(int channel){
        this.channel = channel;
    }

    @Override
    public void turnOn() {
        System.out.println("tv on");
    }

    @Override
    public void turnOff() {
        System.out.println("tv off");
    }
    public static void main(String[] args) {
        Television tv = new Television();
        RemoteControl remoteControl = new Television();
        RemoCon remoCon = new RemoCon(tv);

        remoteControl.turnOn();
        remoteControl.turnOff();

        remoCon.turnOn();
        remoCon.turnOff();
        tv.setChannel(7);
        tv.volumeUp();
    }
}
