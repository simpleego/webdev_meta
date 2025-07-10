package array;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        // 틱택토 게임
        // 게임판 준비(2차원 배열)
        final int ROW = 3;
        final int COL = 3;
        char[][] board = new char[ROW][COL];
        int x,y;
        int change = 0;
        Scanner kbd = new Scanner(System.in);

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = ' ';
            }
        }

        do {
            // 1. 게임판 그리기
            drawBoard(board, ROW);

            // 2. 게임 운영
            // 좌표 선택
            System.out.println("다음 수의 좌표(x,y)를 입력하시오.");
            x = kbd.nextInt();
            y = kbd.nextInt();

            System.out.println("x:" + x);
            System.out.println("y:" + y);

            if (board[x][y] != ' ') {
                System.out.println("이미 돌이 있습니다.");
            } else {
                if(change == 0){
                    board[x][y] = '●';
                    change = 1;
                }else {
                    board[x][y] = '○';
                    change = 0;
                }
            }

            if(x ==-1 || y == -1){
                break;
            }

            // 4. 게임 승리 확정

        }while (true);

    }

    private static void drawBoard(char[][] board, int row) {
        for (int i = 0; i <row ; i++) {
            System.out.println("  "+board[i][0]+"| "+
                    board[i][1]+" | "+board[i][2]);
            if(i != 2){
                System.out.println("---|---|---");
            }
        }
    }
}
