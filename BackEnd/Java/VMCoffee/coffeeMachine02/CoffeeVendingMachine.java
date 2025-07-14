package coffeeMachine02;

import java.util.Scanner;

public class CoffeeVendingMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 자판기 전체 재료 보유량 초기 설정
        CoffeeMaterials materials = new CoffeeMaterials();
        materials.setMilkAmount(500);
        materials.setSugarAmount(300);
        materials.setCoffeeAmount(200);
        materials.setWaterAmount(1000);
        materials.setCupAmount(10);

        // 메뉴 정보 출력
        System.out.println("------ 커피 자판기 메뉴 ------");
        System.out.println("1. 밀크커피");
        System.out.println("2. 설탕커피");
        System.out.println("3. 블랙커피");
        System.out.print("원하는 메뉴 번호를 입력하세요: ");
        int choice = scanner.nextInt();
        System.out.println();

        // 커피별 소비량 설정
        int milkUse = 0, sugarUse = 0, coffeeUse = 0, waterUse = 0, cupUse = 1;

        switch (choice) {
            case 1: // 밀크커피
                milkUse = 100;
                sugarUse = 10;
                coffeeUse = 5;
                waterUse = 150;
                System.out.println("▶ 밀크커피를 선택하셨습니다.");
                break;
            case 2: // 설탕커피
                sugarUse = 20;
                coffeeUse = 5;
                waterUse = 150;
                System.out.println("▶ 설탕커피를 선택하셨습니다.");
                break;
            case 3: // 블랙커피
                coffeeUse = 5;
                waterUse = 200;
                System.out.println("▶ 블랙커피를 선택하셨습니다.");
                break;
            default:
                System.out.println("⚠️ 잘못된 메뉴 번호입니다.");
                scanner.close();
                return;
        }

        // 재료가 충분한지 확인
        if (materials.getMilkAmount() >= milkUse &&
                materials.getSugarAmount() >= sugarUse &&
                materials.getCoffeeAmount() >= coffeeUse &&
                materials.getWaterAmount() >= waterUse &&
                materials.getCupAmount() >= cupUse) {

            // 재료 소비
            materials.setMilkAmount(materials.getMilkAmount() - milkUse);
            materials.setSugarAmount(materials.getSugarAmount() - sugarUse);
            materials.setCoffeeAmount(materials.getCoffeeAmount() - coffeeUse);
            materials.setWaterAmount(materials.getWaterAmount() - waterUse);
            materials.setCupAmount(materials.getCupAmount() - cupUse);

            System.out.println("☕ 커피가 만들어졌습니다!");
        } else {
            System.out.println("❌ 재료가 부족하여 커피를 만들 수 없습니다.");
            scanner.close();
            return;
        }

        // 남은 재료 상태 출력
        System.out.println("\n🧾 남은 자판기 재료 상태:");
        materials.printInfo();

        scanner.close();
    }
}