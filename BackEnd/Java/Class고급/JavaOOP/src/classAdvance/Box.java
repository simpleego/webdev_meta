package classAdvance;

public class Box {
    private int width;
    private int height;
    private int volume;
    private int length;

    public Box(int height, int width, int length) {
        this.height = height;
        this.width = width;
        this.length = length;
        volume = width*height*length;
    }

    public int getVolume(){
        return volume;
    }

    public static void main(String[] args) {
        Box box, box1;

        box = new Box(10, 20, 30);
        box1 = new Box(20, 30, 40);


        int volume = box.getVolume();
        System.out.println("박스 부피:"+volume);

    }
}
