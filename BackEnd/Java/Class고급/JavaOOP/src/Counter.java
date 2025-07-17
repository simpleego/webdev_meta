package classAdvance;

public class Counter {
    int value;

    public void inc(Counter ctr) {
        //a = a+1;
        //a += 1;
         this.value = ctr.value + 1;
        //return ++a;
        System.out.println(value);
    }

    public void reset(){
        value = 0;
        System.out.println(value);
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        System.out.println("카운터:"+counter.value);

        counter.inc(counter);
        counter.inc(counter);
        counter.inc(counter);
        counter.reset();
        counter.inc(counter);
        counter.inc(counter);
        counter.inc(counter);
        counter.inc(counter);
        counter.inc(counter);
//        int x=10;
//        int y;
//
//        y = counter.inc(x);
//
//        System.out.println("x:"+x);
//        System.out.println("y:"+y);
    }
}
