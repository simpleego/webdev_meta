package inheritance;

public class Shape {
    private int x;
    protected int y;

    public Shape() {
        System.out.println("좌표가 생성되었습니다.");
    }

    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void print(){
        System.out.println("x좌표:"+x+"y좌표:"+y);
    }
}
