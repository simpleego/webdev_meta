package inheritance;

public class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle() {
        System.out.println("사각형이 생성됩니다.");
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double area(){
        return width*height;
    }

    public void draw(){
        System.out.println("("+getX()+","+getY()+
                ")"+"위치에"+"가로:"+width+" 세로:"+
                height);
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(10,20);

        rectangle.setX(4);
        rectangle.setY(5);
        rectangle.draw();

    }
}
