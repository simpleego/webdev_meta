🚗 드라이브

---

### 🧱 클래스 도출

| 클래스 이름   | 역할 설명                                     |
|--------------|-----------------------------------------------|
| `Car`         | 자동차의 상태(속도, 시동 여부 등)를 관리         |
| `Driver`      | 자동차를 운전하는 사람, 자동차에 명령을 전달      |

---

### 🚙 `Car` 클래스 예시
```java
public class Car {
    private boolean engineOn = false;
    private int speed = 0;

    public void startEngine() {
        engineOn = true;
        System.out.println("🚗 자동차 시동이 걸렸습니다.");
    }

    public void stopEngine() {
        engineOn = false;
        speed = 0;
        System.out.println("🛑 자동차 시동이 꺼졌습니다.");
    }

    public void accelerate() {
        if (engineOn) {
            speed += 10;
            System.out.println("💨 자동차가 가속합니다. 현재 속도: " + speed + "km/h");
        } else {
            System.out.println("⚠️ 시동이 꺼져 있어 가속할 수 없습니다.");
        }
    }

    public void brake() {
        if (speed > 0) {
            speed -= 10;
            System.out.println("🅿️ 브레이크! 현재 속도: " + speed + "km/h");
        } else {
            System.out.println("🚙 자동차가 이미 멈춰 있습니다.");
        }
    }
}
```

---

### 🧑‍✈️ `Driver` 클래스 예시
```java
public class Driver {
    private String name;
    private Car car;

    public Driver(String name, Car car) {
        this.name = name;
        this.car = car;
    }

    public void drive() {
        System.out.println("\n👤 " + name + "이(가) 운전을 시작합니다.");
        car.startEngine();
        car.accelerate();
        car.accelerate();
        car.brake();
        car.stopEngine();
        System.out.println("🚦 운전 종료\n");
    }
}
```

---

### 🎬 실행 예제
```java
public class Main {
    public static void main(String[] args) {
        Car myCar = new Car();
        Driver driver = new Driver("홍길동", myCar);
        driver.drive();
    }
}
```

---

이렇게 간단한 구조로도 **클래스 간 메시지 전달**(메소드 호출)을 통해 객체 협력 관계를 효과적으로 모델링할 수 있어요. 더 복잡한 시뮬레이션으로 확장하고 싶다면, 자동차 종류, 연료, 자율주행 기능 같은 것도 넣을 수 있어요. 원하시나요? 😎
