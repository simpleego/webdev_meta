package coffeeMachine;

import coin.CoinBox;
import merterials.CoffeeMerterials;

import java.util.Scanner;

public class CoffeeMachine {
    CoffeeMerterials coffeeMerterials;
    CoinBox coinBox;
    Scanner kbd = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        CoinBox coinBox = new CoinBox();
        coinBox.fillCoins();

        CoffeeMerterials coffeeMtr = new CoffeeMerterials();
        coffeeMtr.fillMerterials();
        System.out.println(coffeeMtr);

        //coffeeMachine.coffeeMerterials.pourWater(10);
        // 블랙커피 만들기
        coffeeMtr.pourCup(1);
        coffeeMtr.pourCoffee(10);
        coffeeMtr.pourWater(10);

        System.out.println(coffeeMtr);

        Scanner kbd = new Scanner(System.in);
        coinBox.inputCoins(kbd);
    }
}
