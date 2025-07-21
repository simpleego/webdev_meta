package classAdvance;
abstract class Shape{
    private int x;
    private int y;

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void draw();

//    public Shape() {
//        System.out.println("Shape 기본 생성자 호출됨");
//    }


    public Shape(String msg) {
        System.out.println("Shape 생성자"+msg);
    }

    public void showShape(){
        System.out.println("어떤 모양을 갖는 클래스입니다.");
    }
}

class Rect extends Shape{
    public Rect(){
        super("사각형 모양을 만들꺼야");

        System.out.println("사각형 생성자() 호출됨");
    }

    public void showRec(){
        super.showShape();
        System.out.println("사각형으로 그립니다.");
    }

    @Override
    public void draw() {
        System.out.println("사각형 모양으로 그립니다.");
    }
}

public class ShapeTest {
    public static void main(String[] args) {
        //new Shape();
        Rect rect = new Rect();
        rect.showShape();
        rect.showRec();
        rect.draw();
        rect.move(10,20);

        Shape rect1 = new Rect();
        rect1.move(1,5);
    }
}
