package classAdvance;

public class MyCounter {
    int counter;

    public MyCounter() {
        counter = 10;
    }

    public MyCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "MyCounter{" +
                "counter=" + counter +
                '}';
    }


    public static void main(String[] args) {
        MyCounter myCounter = new MyCounter();
        MyCounter myCounter1 = new MyCounter(10);

    }
}
