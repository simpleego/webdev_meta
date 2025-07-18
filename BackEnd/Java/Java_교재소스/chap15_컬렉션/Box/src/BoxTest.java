
public class BoxTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Box b = new Box();

		b.set("Hello World!");   		// ① 문자열 객체를 저장
		String s = (String)b.get();	// ② Object 타입을 String 타입으로 형변환

		b.set(new Integer(10));   		// ③ 정수 객체를 저장
		Integer i = (Integer)b.get( );	//  ④ Object 타입을 Integer 타입으로 형변환

	}

}
