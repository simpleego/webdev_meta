package classAdvance;

public class Date {
    private int year;
    private String month;
    private int day;

    public Date() {
        this(1900,"1월",1);
    }

    public Date(int year) {
        this(year,"1월",1);
    }

    public Date(int year, String month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setDate(int year, String month, int day) {
        this.year = year;
        this.day = day;
        this.month = month;
    }

    public void printDay() {
        System.out.println( "Date{" +
                "year=" + year +
                ", month='" + month + '\'' +
                ", day=" + day +
                '}');
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        date.printDay();

    }
}
