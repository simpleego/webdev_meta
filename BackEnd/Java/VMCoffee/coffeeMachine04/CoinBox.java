package coffeeMachine04;

import java.util.Scanner;

public class CoinBox {
    private int balance;
    private final int[] typesOfCoins = {500, 100, 50, 10};  // 동전 종류
    private int[] coinCounts = new int[4];                  // 각 동전 종류별 개수

    // 생성자: 초기 동전 수량 설정
    public CoinBox(int coin500, int coin100, int coin50, int coin10) {
        coinCounts[0] = coin500;
        coinCounts[1] = coin100;
        coinCounts[2] = coin50;
        coinCounts[3] = coin10;
        balance = 0;
    }

    public void insert(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("💰 " + amount + "원이 투입되었습니다. 현재 잔액: " + balance + "원");
        } else {
            System.out.println("⚠️ 올바른 금액을 입력해주세요.");
        }
    }

    public boolean deduct(int price) {
        if (balance >= price) {
            balance -= price;
            return true;
        } else {
            System.out.println("❌ 잔액 부족: " + balance + "원 (필요: " + price + "원)");
            return false;
        }
    }

    public void refund() {
        System.out.println("🔁 잔돈 반환 중...");

        int remaining = balance;
        int[] refundCoins = new int[4]; // 반환할 동전 수

        for (int i = 0; i < typesOfCoins.length; i++) {
            int coinValue = typesOfCoins[i];
            int maxAvailable = coinCounts[i];
            int needed = remaining / coinValue;

            if (needed > 0) {
                int give = Math.min(needed, maxAvailable);
                refundCoins[i] = give;
                coinCounts[i] -= give;
                remaining -= coinValue * give;
            }
        }

        if (remaining > 0) {
            System.out.println("❌ 잔돈 반환 불가: 충분한 동전이 없습니다.");
        } else {
            System.out.println("💸 반환된 잔돈:");
            for (int i = 0; i < typesOfCoins.length; i++) {
                if (refundCoins[i] > 0) {
                    System.out.println(" - " + typesOfCoins[i] + "원 동전: " + refundCoins[i] + "개");
                }
            }
            balance = 0;
        }
    }

    public int getBalance() {
        return balance;
    }

    public void printCoinStatus() {
        System.out.println("🪙 현재 동전 보유 상태:");
        for (int i = 0; i < typesOfCoins.length; i++) {
            System.out.println(" - " + typesOfCoins[i] + "원: " + coinCounts[i] + "개");
        }
    }

    public void insertByType(int coinType, int count) {
        int index = -1;
        for (int i = 0; i < typesOfCoins.length; i++) {
            if (typesOfCoins[i] == coinType) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("❌ 지원하지 않는 동전 종류입니다: " + coinType + "원");
            return;
        }

        if (count <= 0) {
            System.out.println("⚠️ 동전 개수는 1개 이상이어야 합니다.");
            return;
        }

        int amount = coinType * count;
        balance += amount;
        coinCounts[index] += count;

        System.out.println("💰 " + coinType + "원 동전 " + count + "개가 투입되었습니다. 잔액: " + balance + "원");
    }

    public void inputCoins(Scanner scanner) {
        System.out.println("🪙 동전 투입 (종류별 입력)");
        System.out.println("입력 가능한 동전: 500원, 100원, 50원, 10원");

        while (true) {
            System.out.print("동전 종류 입력 (종료는 0): ");
            int coinType = scanner.nextInt();
            if (coinType == 0) break;

            System.out.print(coinType + "원 동전 몇 개 넣을까요? ");
            int count = scanner.nextInt();

            insertByType(coinType, count);
        }

        System.out.println("✅ 동전 입력 완료. 현재 잔액: " + getBalance() + "원\n");
    }

}