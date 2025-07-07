package controlloop;

import java.util.Calendar;
import java.util.Date;

public class Welcome {
    public static void main(String[] args) {
        //Date date = new Date();
        //int hour = date.getHours();
        int hour;

        // Calendar 클래스를 사용해야 한다.
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);

        System.out.println(hour);
    }
}
