package inheritance;

public class Lion extends Animal{
    // Animal + 추가 속성 및 기능 기술
    private int legs=4;

    public String getPicture(){
        return picture;
    }

    public Lion() {
        System.out.println("사자가 생성됩니다.");
    }

    public void roar(){
        System.out.println("사자가 울부짓다...");
    }

}
