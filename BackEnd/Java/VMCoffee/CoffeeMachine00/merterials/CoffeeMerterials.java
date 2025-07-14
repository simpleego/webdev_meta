package merterials;

public class CoffeeMerterials {
    private int milkAmount;
    private int sugarAmount;
    private int coffeeAmount;
    private int cupAmount;
    private int waterAmount;

    // 객체 생성 초기에 필요한 재료 채우기
    public  void fillMerterials(){
        milkAmount = 100;
        sugarAmount = 100;
        coffeeAmount = 100;
        cupAmount = 100;
        waterAmount = 100;
    }

    public  void fillMerterials(
            int milkAmount,
            int sugarAmount,
            int coffeeAmount,
            int cupAmount,
            int waterAmount
    ){
        this.milkAmount = milkAmount;
        this.sugarAmount = sugarAmount;
        this.coffeeAmount = coffeeAmount;
        this.cupAmount = cupAmount;
        this.waterAmount = waterAmount;
    }

    public int getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(int milkAmount) {
        this.milkAmount -= milkAmount;
    }

    public int getSugarAmount() {
        return sugarAmount;
    }

    public void setSugarAmount(int sugarAmount) {
        this.sugarAmount = sugarAmount;
    }

    public int getCoffeeAmount() {
        return coffeeAmount;
    }

    public void setCoffeeAmount(int coffeeAmount) {
        this.coffeeAmount = coffeeAmount;
    }

    public int getCupAmount() {
        return cupAmount;
    }

    public void setCupAmount(int cupAmount) {
        this.cupAmount = cupAmount;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public void pourMilk(int amount){
        milkAmount -= amount;
    }
    public void pourSugar(int amount){
        sugarAmount -= amount;
    }
    public void pourCoffee(int amount){
        coffeeAmount -= amount;
    }
    public void pourCup(int amount){
        cupAmount -= amount;
    }
    public void pourWater(int amount){
        waterAmount -= amount;
    }

    @Override
    public String toString() {
        return "CoffeeMerterials{" +
                "milkAmount=" + milkAmount +
                ", sugarAmount=" + sugarAmount +
                ", coffeeAmount=" + coffeeAmount +
                ", cupAmount=" + cupAmount +
                ", waterAmount=" + waterAmount +
                '}';
    }
}