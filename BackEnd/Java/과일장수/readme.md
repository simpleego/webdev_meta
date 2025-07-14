🍎 좋은 아이디어예요! 이 상황을 모델링하기 위해 객체 지향적으로 접근해볼 수 있어요. 먼저 필요한 클래스들을 도출하고, 각 클래스의 역할을 정리한 뒤 간단한 자바 코드 예시를 보여드릴게요.

---

### 🧱 필요한 클래스 도출

| 클래스 이름     | 역할 설명                                                |
|----------------|----------------------------------------------------------|
| `Fruit`         | 과일의 이름, 가격 등을 저장하는 클래스                   |
| `Seller`        | 과일을 보유하고 판매하는 과일장수 클래스                  |
| `Customer`      | 과일을 구매하려는 손님 클래스                             |
| `Market`        | 판매와 구매를 중개하는 클래스 (선택사항, 시스템 관리용)    |

---

### ✨ 클래스 설계 예시

#### 1. `Fruit` 클래스
```java
public class Fruit {
    private String name;
    private int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return name + " (" + price + "원)";
    }
}
```

#### 2. `Seller` 클래스
```java
import java.util.ArrayList;
import java.util.List;

public class Seller {
    private String name;
    private List<Fruit> inventory = new ArrayList<>();

    public Seller(String name) {
        this.name = name;
    }

    public void addFruit(Fruit fruit) {
        inventory.add(fruit);
    }

    public Fruit sellFruit(String fruitName) {
        for (Fruit fruit : inventory) {
            if (fruit.getName().equalsIgnoreCase(fruitName)) {
                inventory.remove(fruit);
                System.out.println(name + "이(가) " + fruit + "을(를) 판매했습니다.");
                return fruit;
            }
        }
        System.out.println("해당 과일은 없습니다.");
        return null;
    }
}
```

#### 3. `Customer` 클래스
```java
public class Customer {
    private String name;
    private int money;

    public Customer(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void buyFruit(Seller seller, String fruitName) {
        Fruit fruit = seller.sellFruit(fruitName);
        if (fruit != null && fruit.getPrice() <= money) {
            money -= fruit.getPrice();
            System.out.println(name + "이(가) " + fruit + "을(를) 구매했습니다. 잔액: " + money + "원");
        } else if (fruit != null) {
            System.out.println(name + "은(는) 돈이 부족합니다.");
            seller.addFruit(fruit); // 다시 재고에 넣기
        }
    }
}
```

---

### 🎬 간단한 실행 예제
```java
public class MarketTest {
    public static void main(String[] args) {
        Seller seller = new Seller("과일장수 김씨");
        seller.addFruit(new Fruit("사과", 1000));
        seller.addFruit(new Fruit("바나나", 1500));

        Customer customer = new Customer("손님 박씨", 2000);
        customer.buyFruit(seller, "사과");
        customer.buyFruit(seller, "바나나");
    }
}
```

---

필요하면 `Market` 클래스에서 모든 거래를 관리하거나 로그를 저장하는 구조로 확장도 가능해요. 더 복잡한 시나리오로 확장해보고 싶으신가요? 🍊🍇💡
