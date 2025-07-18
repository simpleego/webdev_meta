class Counter {
	private int value = 0;
	public synchronized  void increment() {   value++;	}
	public synchronized  void decrement() {   value--;	}
	public synchronized  void printCounter() {		System.out.println(value);	}
}

//실행결과는 컴퓨터와 상황에 따라서 상당히 달라진다.
//스레드 간섭이 없다면 모두 0이 출력되어야 한다.  

class MyThread extends Thread {

//공유된 Counter 객체의 참조값을 저장한다. 
	Counter sharedCounter;

	public MyThread(Counter c) {
		this.sharedCounter = c;
	}

//증가햿다가 감소시키기 때문에 카운터의 값은 변화가 없어야 한다. 

	public void run() {
		int i = 0;
		while (i < 20000) {

//가끔 카운터의 값을 출력하여 본다. 
			sharedCounter.increment();
			sharedCounter.decrement();
			if (i % 40 == 0)
				sharedCounter.printCounter();
			try {
				sleep((int) (Math.random() * 2));

//난수 시간만큼 스레드를 중지한다. 
			} catch (InterruptedException e) {	}
			i++;
		}
	}
}

public class CounterTest {
	public static void main(String[] args) {

//공유 카운터 객체를 생성한다.
		Counter c = new Counter();
		new MyThread(c).start();

//확실하게 잘못된 결과를 내기 위하여 스레드를 4개나 생성하여 실행한다.
		new MyThread(c).start();
		new MyThread(c).start();
		new MyThread(c).start();
	}
}