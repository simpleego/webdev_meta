package classex;

public class TV {
    int channel;
    int volume;
    boolean onOff;

    // TV 전원을 on/off 기능을 만들어 보세요.
    void powerOn(){
        onOff = true;
    }
    void powerOff(){
        onOff = false;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
       this.channel = channel;
    }

    public void channelUp(){
        channel++;
    }
    public void channelDown(){
        channel--;
    }

    public int getVolume() {
        return volume;
    }

    public void volumeUp() {
        volume++;
    }
    public void volumeDown() {
        volume--;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    @Override
    public String toString() {
        return "TV [" +
                "채널=" + channel +
                ", 볼륨=" + volume +
                ", 전원=" + onOff +
                ']';
    }

    //    void print(){
//        System.out.println("전원:"+onOff);
//        System.out.println("볼륨:"+volume);
//        System.out.println("채널:"+channel);
//    }

}
