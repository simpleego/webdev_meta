package classAdvance;

public class Rectangle {
    private int x;
    private int y;
    private int width, height;

    public Rectangle() {
        this(0,0,1,1);
    }

    public Rectangle(int width, int height) {
       this(0,0,width,height);
    }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        System.out.println(rectangle);

        Rectangle rectangle1 = new Rectangle(100,200);
        System.out.println(rectangle1);

    }
}
