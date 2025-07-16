package classAdvance;

public class TV {
    private int channel;
    private int vol;
    private boolean onOff;

    public TV(int channel, int vol, boolean onOff) {
        this.channel = channel;
        this.vol = vol;
        this.onOff = onOff;
    }

    public TV() {
        channel = 10;
        vol = 10;
        onOff = false;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getVol() {
        return vol;
    }

    public void setVol(int vol) {
        this.vol = vol;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    @Override
    public String toString() {
        return "TV{" +
                "channel=" + channel +
                ", vol=" + vol +
                ", onOff=" + onOff +
                '}';
    }

    public static void main(String[] args) {
        TV tv = new TV();

        TV myTv = new TV(10,20,true);

        System.out.println(myTv);
    }
}
