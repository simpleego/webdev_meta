package classex;

public class TvTest {
    public static void main(String[] args) {

        TV tv = new TV();
        tv.powerOn();
        //System.out.println(tv.toString());
        tv.volumeUp();
        tv.volumeUp();
        tv.volumeUp();
        tv.volumeUp();
        System.out.println(tv);
        tv.channelUp();
        tv.channelUp();
        tv.channelUp();
        tv.channelUp();
        System.out.println(tv);
        tv.setChannel(20);
        System.out.println(tv);

    }
}
