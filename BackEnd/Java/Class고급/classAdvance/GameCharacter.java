package classAdvance;

import java.util.ArrayList;

public class GameCharacter {

    private String charName;

    private ArrayList<GameItem> itemList = new ArrayList<>();

    public class GameItem{
        String name;
        int type;
        int price;

        int getPrice(){
            return price;
        }

        @Override
        public String toString() {
            return "GameItem{" +
                    "name='" + name + '\'' +
                    ", type=" + type +
                    ", price=" + price +
                    '}';
        }
        public String getCharacterName(){
            return charName;
        }
    }
    public void add(String name, int type, int price){
        GameItem item = new GameItem();
        item.name = name;
        item.type = type;
        item.price = price;

        itemList.add(item);
    }

    public void print(){
        int total = 0;
        for(GameItem item : itemList){
            System.out.println("아이템:"+item);
            total += item.getPrice();
        }
        System.out.println("아이템 총액:"+total);
    }

    public static void main(String[] args) {

        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.add("Swoord",1,100);
        gameCharacter.add("Gun",2,50);
        gameCharacter.add("갑옷",3,150);

        gameCharacter.print();
    }

}
