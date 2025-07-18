public class Car {
    
   private String model;
       private String color;
       private int speed;

        // 자동차의 시리얼 번호
       private int id;
        // 실체화된 Car 객체의 개수를 위한 정적 변수
       private static int numbers = 0;
 
       public Car(String m, String c, int s) {
             model = m;
             color = c;
             speed = s;
              // 자동차의 개수를 증가하고 id에 대입한다. 
             id = ++numbers;
       }
      

	// 정적 메소드 
       public static int getNumberOfCars() {
             return numbers; // OK!
       }
}