package classAdvance;

public class Circle {
    Point center;
    int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    public static void main(String[] args) {
        // 무엇을 먼저 객체 생성하는가?
        Circle circle = new Circle(new Point(10,20),50);

        System.out.println(circle);
    }
}
