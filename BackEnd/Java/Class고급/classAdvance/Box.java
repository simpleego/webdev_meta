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

    public  Box whosLargest(Box box1, Box box2){
        if(box1.volume > box2.volume)
            return box1;
        else
            return box2;
    }

    public  Box heightLargest(Box box1, Box box2){
        if(box1.height > box2.height)
            return box1;
        else
            return box2;
    }

    @Override
    public String toString() {
        return "Box{" +
                "width=" + width +
                ", height=" + height +
                ", volume=" + volume +
                ", length=" + length +
                '}';
    }

    public static void main(String[] args) {
        Box box, box1;

        box = new Box(10, 20, 30);
        box1 = new Box(20, 30, 40);


        int volume = box.getVolume();
        System.out.println("박스 부피:"+volume);

        Box bigBbox = box.whosLargest(box,box1);
        System.out.println(bigBbox);

        Box heightBig = box.heightLargest(box,box1);
        System.out.println(heightBig);

    }
}
