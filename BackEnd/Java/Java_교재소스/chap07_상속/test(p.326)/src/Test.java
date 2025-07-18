class Shape {
	
       public Shape() {	
             System.out.println("Shape 생성자() ");
       }
};
 
class Rectangle extends Shape {
       public Rectangle(){		
		 super();		// 명시적인 호출
             System.out.println("Rectangle 생성자()");
       }
};

public class Test {
	public static void main(String[] args) {
		Rectangle r = new Rectangle();
	}
};