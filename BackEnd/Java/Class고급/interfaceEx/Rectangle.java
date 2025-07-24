package interfaceEx;

public class Rectangle implements Comparable{

    private int width;
    private int height;

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

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(100, 200);
        Rectangle rectangle1 = new Rectangle(100, 200);

        int big = rectangle.compareTo(rectangle1);

        if(big > 0){
            System.out.println("내 사각형이 면적이 크다.");
        } else if (big < 0) {
            System.out.println("상대 사각형이 면적이 크다.");
        }else {
            System.out.println("두개의 사각형 면적이 같다.");

        }
    }

    @Override
    public int compareTo(Object other) {
        Rectangle otherRect = (Rectangle) other;

        if (this.getArea() < otherRect.getArea()) {
            return -1;
        } else if (this.getArea() > otherRect.getArea()){
            return 1;
        }else {
            return 0;
        }

    }

    private int getArea() {
        return width*height;
    }
}
