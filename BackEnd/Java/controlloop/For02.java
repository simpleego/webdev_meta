package controlloop;

public class For02 {
    public static void main(String[] args) {
        // 100개의 난수 생성 후 그 값을 모두 더하라.
        // 단, 난수의 범위는 1~100까지로 생성하시오.
        int sum=0;
        int rnd;

        // 범위 내의 난수 생성: (int)(Math.random() * (최댓값 - 최솟값 + 1)) + 최솟값
        // randomNum = (int)(Math.random() * (max - min + 1)) + min;

        for(int i=0; i<100; i++){
            // 난수 생성
            rnd  = (int)(Math.random()*3+5);
            sum += rnd;
            System.out.println("난수 :"+i+":"+rnd);
        }
        System.out.println("난수의 합:"+sum);
    }
}
