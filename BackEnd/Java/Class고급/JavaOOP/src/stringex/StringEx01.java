package stringex;

import java.util.Calendar;

public class StringEx01 {
    public static void main(String[] args) {
        String id = new String("020625-4403619");
        char gender = id.charAt(7);
        Calendar calendar = Calendar.getInstance();

        int thisYear= calendar.get(Calendar.YEAR);;
        int baseYear=1900;
        int age;
        int year = Integer.parseInt(id.substring(0,2));
        String month;
        System.out.println(year);
        month = id.substring(2,4);

        if (month.equals("06")) {
            System.out.println("태어난 월 :"+month);
        }




//        String str1 = String.valueOf(id.charAt(7));
//        System.out.println("남/여 :"+str1);
//        String str = id.charAt(7)+"";

        switch (gender) {
            case '1':
                baseYear=1900+year;
                break;
            case '3':
                System.out.println("남자");
                baseYear = 2000+year;
                break;
            case '2':
                baseYear=1900+year;
                break;
            case '4':
                System.out.println("여자");
                baseYear = 2000+year;
                break;
        }

        age = thisYear - baseYear;
        System.out.println("나이:"+age);
    }
}
