package array;

import java.util.Scanner;

public class Array09 {
    public static void main(String[] args) {
        final int SIZE = 3;
        int seet[] = new int[SIZE];
        int userNum;
        Scanner kbd = new Scanner(System.in);

        showSheet(seet);
        System.out.print("원하는 좌석번호를 입력하세요(종료: -1)");
        userNum = kbd.nextInt();
        // 예약 가능한지 체크
        if(seet[userNum] == 0){
            seet[userNum] = 1;  // 좌석예약 처리
            System.out.println(userNum+"는 예약되었습니다.");
        }else {
            System.out.println(userNum+"는 이미예약되었습니다.");
        }
        // 매진 상황 체크
        int seetCount=0;
        for (int i = 0; i < seet.length; i++) {
            if(seet[i] ==0 ){
                seetCount++;
            }
        }

        showSheet(seet);
        System.out.print("원하는 좌석번호를 입력하세요(종료: -1)");
        userNum = kbd.nextInt();

        if(seetCount > 0){
            // 예약 가능한지 체크
            if(seet[userNum] == 0){
                seet[userNum] = 1;  // 좌석예약 처리
                System.out.println(userNum+"는 예약되었습니다.");
            }else {
                System.out.println(userNum+"는 이미예약되었습니다.");
            }
        }else {
            System.out.println("모든 좌석이 예약되었습니다.");
        }

        // ---------------
        seetCount=0;
        for (int i = 0; i < seet.length; i++) {
            if(seet[i] ==0 ){
                seetCount++;
            }
        }
        showSheet(seet);
        System.out.print("원하는 좌석번호를 입력하세요(종료: -1)");
        userNum = kbd.nextInt();

        if(seetCount > 0){
            // 예약 가능한지 체크
            if(seet[userNum] == 0){
                seet[userNum] = 1;  // 좌석예약 처리
                System.out.println(userNum+"는 예약되었습니다.");
            }else {
                System.out.println(userNum+"는 이미예약되었습니다.");
            }
        }else {
            System.out.println("모든 좌석이 예약되었습니다.");
        }
        //----------------------
        // ---------------
        seetCount=0;
        for (int i = 0; i < seet.length; i++) {
            if(seet[i] ==0 ){
                seetCount++;
            }
        }
        showSheet(seet);
        System.out.print("원하는 좌석번호를 입력하세요(종료: -1)");
        userNum = kbd.nextInt();

        if(seetCount > 0){
            // 예약 가능한지 체크
            if(seet[userNum] == 0){
                seet[userNum] = 1;  // 좌석예약 처리
                System.out.println(userNum+"는 예약되었습니다.");
            }else {
                System.out.println(userNum+"는 이미예약되었습니다.");
            }
        }else {
            System.out.println("모든 좌석이 예약되었습니다.");
        }
        //----------------------
        // ---------------
        seetCount=0;
        for (int i = 0; i < seet.length; i++) {
            if(seet[i] ==0 ){
                seetCount++;
            }
        }
        showSheet(seet);
        System.out.print("원하는 좌석번호를 입력하세요(종료: -1)");
        userNum = kbd.nextInt();

        if(seetCount > 0){
            // 예약 가능한지 체크
            if(seet[userNum] == 0){
                seet[userNum] = 1;  // 좌석예약 처리
                System.out.println(userNum+"는 예약되었습니다.");
            }else {
                System.out.println(userNum+"는 이미예약되었습니다.");
            }
        }else {
            System.out.println("모든 좌석이 예약되었습니다.");
        }
        //----------------------
    }

    private static void showSheet(int[] seet) {
        System.out.println("---------------------------------");
        for (int i = 0; i < seet.length; i++) {
            System.out.printf("%3d",i);
        }
        System.out.println("\n---------------------------------");
        for (int i = 0; i < seet.length; i++) {
            System.out.printf("%3d",seet[i]);
        }
        System.out.println("\n---------------------------------");
    }
}