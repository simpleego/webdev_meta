package array;

public class Array08 {
    public static void main(String[] args) {
        final int SIZE = 6;
        int freq[] = new int[SIZE];
        int index=0;

        for (int i = 0; i < 10000; i++) {
              index = (int)(Math.random()*SIZE);
              freq[index]++;
        }

        System.out.println(" 주사위 빈도 수");

        int sum=0;
        for (int i = 0; i < freq.length; i++) {
            sum += freq[i];
        }
        int avg = sum/SIZE;

        int []diff = new int[SIZE];

        for (int i = 0; i < freq.length; i++) {
            diff[i] = Math.abs(avg - freq[i]);
        }

        System.out.println(" 평균 빈도수 : "+avg);
        for (int i = 0; i < freq.length; i++) {
            System.out.printf("%d : %d -- %d\n",i+1,freq[i], diff[i]);
        }

    }
}