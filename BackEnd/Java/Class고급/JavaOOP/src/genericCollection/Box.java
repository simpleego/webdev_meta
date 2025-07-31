package genericCollection;

public class Box <T>{
    private T data;
    public void set(T data){
        this.data = data;
    }
    public T get(){ return data; }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>();
        stringBox.set("안녕하세요.");
        String str = stringBox.get();
        System.out.println(str);

        Box<Integer> integerBox = new Box<>();
        int num = 100;
        integerBox.set(num);
        System.out.println(integerBox.get());
    }
}
