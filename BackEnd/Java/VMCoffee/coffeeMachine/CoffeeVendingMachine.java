package coffeeMachine;

import coffeeMachine.CoinBox;
import java.util.Scanner;

public class CoffeeVendingMachine {
    private CoffeeMaterials materials;
    private CoinBox coinBox;
    private final Scanner scanner = new Scanner(System.in);

    public CoffeeVendingMachine() {
        materials = new CoffeeMaterials();
        coinBox = new CoinBox(10, 20, 30, 50); // 500원, 100원, 50원, 10원 초기 보유량

        materials.setMilkAmount(500);
        materials.setSugarAmount(300);
        materials.setCoffeeAmount(200);
        materials.setWaterAmount(1000);
        materials.setCupAmount(10);
    }

    // 동전 입력: 종류별로 입력받음
    public void inputCoins() {
        System.out.println("🪙 자판기에 동전을 넣어주세요");
        System.out.println("종료하려면 '0'을 입력하세요");

        while (true) {
            System.out.print("동전 종류 입력 (500, 100, 50, 10): ");
            int coinType = scanner.nextInt();
            if (coinType == 0) break;

            System.out.print(coinType + "원 동전 몇 개?: ");
            int count = scanner.nextInt();
            coinBox.insertByType(coinType, count);
        }

        System.out.println("✅ 동전 입력 완료. 현재 잔액: " + coinBox.getBalance() + "원\n");
    }


    // 커피 메뉴 선택 및 재료 처리
    public void showCoffeeMenu() {
        System.out.println("------ 커피 메뉴 ------");
        System.out.println("1. 밀크커피 (1000원)");
        System.out.println("2. 설탕커피 (800원)");
        System.out.println("3. 블랙커피 (700원)");
        System.out.println("9. 관리자 기능 ");
        System.out.println("----------------------");
    }

    // 커피 메뉴 선택 및 재료 처리
    public void serveCoffeeMenu() {
        showCoffeeMenu();
        System.out.print("메뉴 번호를 선택하세요: ");
        int choice = scanner.nextInt();

        int price = 0;
        int milkUse = 0, sugarUse = 0, coffeeUse = 5, waterUse = 150, cupUse = 1;
        String coffeeName = "";

        switch (choice) {
            case 1:
                coffeeName = "밀크커피"; price = 1000;
                milkUse = 100; sugarUse = 10;
                break;
            case 2:
                coffeeName = "설탕커피"; price = 800;
                sugarUse = 20;
                break;
            case 3:
                coffeeName = "블랙커피"; price = 700;
                waterUse = 200;
                break;
            case 9:
                System.out.println("관리자 모드 기능을 구현합니다.");
                System.out.println("구현중임으로 프로그램 종료합니다.");
                System.exit(0);
            default:
                System.out.println("❌ 잘못된 메뉴 번호입니다.");
                return;
        }

        if (!coinBox.deduct(price)) return;

        if (materials.getMilkAmount() >= milkUse &&
                materials.getSugarAmount() >= sugarUse &&
                materials.getCoffeeAmount() >= coffeeUse &&
                materials.getWaterAmount() >= waterUse &&
                materials.getCupAmount() >= cupUse) {

            materials.setMilkAmount(materials.getMilkAmount() - milkUse);
            materials.setSugarAmount(materials.getSugarAmount() - sugarUse);
            materials.setCoffeeAmount(materials.getCoffeeAmount() - coffeeUse);
            materials.setWaterAmount(materials.getWaterAmount() - waterUse);
            materials.setCupAmount(materials.getCupAmount() - cupUse);

            System.out.println("\n☕ '" + coffeeName + "'가 준비되었습니다!");
        } else {
            System.out.println("❌ 재료 부족으로 '" + coffeeName + "'를 만들 수 없습니다.");
            coinBox.insert(price); // 금액 환불
        }

        coinBox.refund();
        System.out.println("\n🧾 현재 재료 상태:");
        materials.printInfo();
    }

    public void run() {
        char running='y';
        do {
            showCoffeeMenu();
            inputCoins();
            serveCoffeeMenu();
            System.out.println("프로그램 종료 y/n ?");
            running = scanner.next().charAt(0);
            if(running == 'y' || running == 'Y') break;
        } while (true);
    }

    public static void main(String[] args) {
        CoffeeVendingMachine machine = new CoffeeVendingMachine();
        machine.run();
    }
}