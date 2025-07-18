class A {
	A() { 	}
}

class B extends A {
	B() { 	}
}

public class TypeTest2 {
	public static void main(String args[]) {
		A a = new B();	// OK!
	}
}