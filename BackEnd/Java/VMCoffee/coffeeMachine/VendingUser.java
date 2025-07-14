package coffeeMachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VendingUser {
    private String userId;
    private String userName;
    private int balance;
    private String purchasedCoffee;
    private String purchaseTime;

    // 생성자
    public VendingUser(String userId, String userName, int balance) {
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
        this.purchasedCoffee = null;
        this.purchaseTime = null;
    }

    // 동전 투입 기능
    public void insertCoin(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("💰 " + amount + "원이 투입되었습니다. 현재 잔액: " + balance + "원");
        } else {
            System.out.println("⚠️ 잘못된 금액입니다.");
        }
    }

    // 동전 반환 기능
    public void returnCoin() {
        System.out.println("🔙 잔액 " + balance + "원이 반환되었습니다.");
        balance = 0;
    }

    // 메뉴 선택 기능
    public boolean selectMenu(String coffeeName, int price) {
        if (balance >= price) {
            purchasedCoffee = coffeeName;
            purchaseTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            balance -= price;
            System.out.println("☕ '" + coffeeName + "' 구매 완료! 잔액: " + balance + "원");
            return true;
        } else {
            System.out.println("❌ 잔액이 부족하여 '" + coffeeName + "'을 구매할 수 없습니다.");
            return false;
        }
    }

    // 커피 꺼내기 기능
    public void pickupCoffee() {
        if (purchasedCoffee != null) {
            System.out.println("🧊 커피 '" + purchasedCoffee + "'를 꺼냈습니다. 구매 시간: " + purchaseTime);
            purchasedCoffee = null;
            purchaseTime = null;
        } else {
            System.out.println("📭 꺼낼 커피가 없습니다.");
        }
    }

    // 사용자 정보 출력
    public void printUserInfo() {
        System.out.println("👤 사용자: " + userName + " (" + userId + ")");
        System.out.println("💵 잔액: " + balance + "원");
        if (purchasedCoffee != null) {
            System.out.println("🛍️ 구매한 커피: " + purchasedCoffee);
            System.out.println("🕒 구매 시간: " + purchaseTime);
        } else {
            System.out.println("🛍️ 구매한 커피 없음");
        }
    }
}
