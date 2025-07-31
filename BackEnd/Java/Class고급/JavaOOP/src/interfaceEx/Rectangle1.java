package interfaceEx;
class Shape{
    protected int x,y;

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
interface Drawable{
    void draw();
}

public class Rectangle1 extends Shape implements Drawable {
    int width,height;

    public Rectangle1(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println(x+","+y+"위치에"+width+"너비"+height
                +"높이"+"를 갖는 사각형을 그린다.");
    }

    public static void main(String[] args) {
        Rectangle1 rectangle1 = new Rectangle1(10,20,10,30);
        rectangle1.draw();

    }
}
