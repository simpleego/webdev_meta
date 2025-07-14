package coin;

import java.util.Scanner;

public class CoinBox {
    private int balance;  // 자판기에 입력된 동전
    private  final int[] typesOfCoins = {500,100,50,10};
    private int[] coinCounts = new int[4];

    // 동전입력
    // 동전출력(판매시)
    // 잔돈 반환
    public void fillCoins(){
        coinCounts[0]=10;
        coinCounts[1]=10;
        coinCounts[2]=10;
        coinCounts[3]=10;
    }

    public void fillCoins(int coin500,
                          int coin100,int coin50,int coin10
    ){
        coinCounts[0]=coin500;
        coinCounts[1]=coin100;
        coinCounts[2]=coin50;
        coinCounts[3]=coin10;
    }

    public void insert(int coins){
        balance += coins;
    }

    public boolean dispense(int price){
        if(balance >= price){
            balance -= price;
            return true;
        }else {
            System.out.println("잔액부족");
            return false;
        }
    }

    // 동전반환
    public void returnCoin(int coins){
        // ??
    }

    public void inputCoins(Scanner kbd){

        System.out.println(" 동전 투입(동전 종류별 입력");
        System.out.println("입력가능한 동전: 500원, 100원, 50원, 10원");
        while (true){
            System.out.println("동전 종류 입력(종료: 0)");
            int coinType = kbd.nextInt();
            if(coinType == 0) break;

            System.out.println(coinType+"원 동전 몇 개를 넣을까요");
            int count = kbd.nextInt();

            insertByType(coinType, count);
        }

    }

    private void insertByType(int coinType, int count) {
        int index = -1;
        for (int i = 0; i < typesOfCoins.length; i++) {
            if(typesOfCoins[i] == coinType) {
                index = i;
                break;
            }
        }

        if(index == -1) {
            System.out.println("지원하지 않는 동전 입력"+coinType+"원");
            return;
        }

        if(count <= 0){
            System.out.println("동전의 개수는 1개 이상이어야 함..");
            return;
        }

        //  동전 종류별 동전입금액 처리
        int amount = typesOfCoins[index] * count;
        balance += amount;
        System.out.println(coinType+"원 동전"+count+"투입되었습니다.");
        System.out.println("잔액 : "+balance);
    }

}
