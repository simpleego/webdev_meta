🛒 물론이죠! `Market` 클래스는 전체 거래를 조율하고, 과일장수(Seller)와 손님(Customer)의 상호작용을 관리하는 중심 컨트롤러 역할을 할 수 있어요. 아래에 간단한 설계를 예시로 보여드릴게요:

---

### 🌟 `Market` 클래스 설계

```java
public class Market {
    private Seller seller;

    public Market(Seller seller) {
        this.seller = seller;
    }

    public void showInventory() {
        System.out.println("\n📦 현재 판매 가능한 과일 목록:");
        seller.getInventory().forEach(fruit -> 
            System.out.println("- " + fruit.getName() + ": " + fruit.getPrice() + "원")
        );
    }

    public void processPurchase(Customer customer, String fruitName) {
        System.out.println("\n💬 [" + customer.getName() + "이(가) " + fruitName + " 구매 시도]");
        customer.buyFruit(seller, fruitName);
    }
}
```

---

### 🔧 Seller 클래스에 getInventory 추가 (Market에서 재고 확인용)
```java
public List<Fruit> getInventory() {
    return inventory;
}
```

---

### 🔧 Customer 클래스에 getName 추가 (Market 로그용)
```java
public String getName() {
    return name;
}
```

---

### 🎬 전체 흐름을 테스트하는 예시
```java
public class MarketSimulation {
    public static void main(String[] args) {
        Seller seller = new Seller("과일장수 김씨");
        seller.addFruit(new Fruit("사과", 1000));
        seller.addFruit(new Fruit("바나나", 1500));
        seller.addFruit(new Fruit("포도", 2000));

        Customer customer = new Customer("손님 박씨", 2500);

        Market market = new Market(seller);
        market.showInventory();

        market.processPurchase(customer, "사과");
        market.processPurchase(customer, "포도");

        market.showInventory();
    }
}
```

---

이렇게 하면 `Market` 클래스가 전체 흐름을 깔끔하게 컨트롤하면서 사용자 경험을 통합적으로 관리할 수 있게 돼요. 원하신다면, 여러 명의 고객을 다루거나 로그 기록 기능까지 추가해서 더 풍부하게 만들어볼 수도 있어요. 확장해보고 싶으세요? 😄
