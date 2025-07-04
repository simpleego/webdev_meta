package DataTypeOperation;

public class DataType04 {
    public static void main(String[] args) {
        float num1 =  123.44579812457897454455F;
        double num2 = 456.1234567890123856789;

        System.out.println("num1:"+num1);
        System.out.println("num2:"+num2);

        boolean isBig = 10 > 3;

        // 부울형 true,false
        boolean gender;
        gender = true;
        if(gender){
            System.out.println("남성입니다.");
        }

        if(!isBig){
            System.out.println("크다");
        }else {
            System.out.println("작다");
        }

        char char1;
        int char2;

        char1 = 'a';
        char2 = '김';

        System.out.println("char1:"+char1);
        System.out.println("char2:"+char2);

        //Character.MAX_VALUE
        char1 = (char) (char1 + 1);
        char2 = (char)(char2 + 1);

        System.out.println("char1:"+char1);
        System.out.println("char2:"+char2);

        String str = "안녕 친구야";

        char name='\uac00';
        name++;
        System.out.println("name:"+name);
    }
}
