package CoffeeMachine03;

import java.util.Scanner;

public class CoffeeVendingMachine {
    private CoffeeMaterials materials;

    // 생성자: 자판기 초기 재료 설정
    public CoffeeVendingMachine() {
        materials = new CoffeeMaterials();
        materials.setMilkAmount(500);
        materials.setSugarAmount(300);
        materials.setCoffeeAmount(200);
        materials.setWaterAmount(1000);
        materials.setCupAmount(10);
    }

    // 커피 메뉴 처리 메서드
    public void serveCoffeeMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("------ 커피 자판기 메뉴 ------");
        System.out.println("1. 밀크커피");
        System.out.println("2. 설탕커피");
        System.out.println("3. 블랙커피");
        System.out.print("원하는 메뉴 번호를 입력하세요: ");
        int choice = scanner.nextInt();
        System.out.println();

        // 각 커피 메뉴의 재료 소비량
        int milkUse = 0, sugarUse = 0, coffeeUse = 0, waterUse = 0, cupUse = 1;
        String coffeeName = "";

        switch (choice) {
            case 1:
                coffeeName = "밀크커피";
                milkUse = 100;
                sugarUse = 10;
                coffeeUse = 5;
                waterUse = 150;
                break;
            case 2:
                coffeeName = "설탕커피";
                sugarUse = 20;
                coffeeUse = 5;
                waterUse = 150;
                break;
            case 3:
                coffeeName = "블랙커피";
                coffeeUse = 5;
                waterUse = 200;
                break;
            default:
                System.out.println("⚠️ 잘못된 메뉴 번호입니다.");
                scanner.close();
                return;
        }

        // 재료 충분성 확인
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

            System.out.println("☕ '" + coffeeName + "'가 만들어졌습니다!");
        } else {
            System.out.println("❌ 재료 부족으로 커피를 만들 수 없습니다.");
            scanner.close();
            return;
        }

        // 남은 재료 출력
        System.out.println("\n🧾 현재 자판기 재료 상태:");
        materials.printInfo();

        scanner.close();
    }

    // 프로그램 실행용 main 메서드
    public static void main(String[] args) {
        CoffeeVendingMachine machine = new CoffeeVendingMachine();
        machine.serveCoffeeMenu();
    }
}
