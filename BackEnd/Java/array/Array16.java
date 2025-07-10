package array;

public class Array16 {
    public static void main(String[] args) {
        // 과목별 평균을 구하시오.

        int sumKor=0;
        int sumEng=0;
        int sumMat=0;

        double avgKor;
        double avgEng;
        double avgMat;

        int[][] sungjuk = {
                {19, 20, 90},
                {90, 85, 95},
                {78, 88, 85},
        };

        int rows = sungjuk.length;
        int cols = sungjuk[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (j){
                    case 0:
                        sumKor += sungjuk[i][j];
                        break;
                    case 1:
                        sumEng += sungjuk[i][j];
                        break;
                    case 2:
                        sumMat += sungjuk[i][j];
                        break;
                }
            }
        }

        avgKor = (double) sumKor / cols;
        avgEng = (double) sumEng / cols;
        avgMat = (double) sumMat / cols;

        System.out.print("국어총점:"+sumKor);
        System.out.printf(" 국어평균: %f\n",avgKor);
        System.out.print("영어총점:"+sumEng);
        System.out.printf(" 영어평균: %f\n",avgEng);
        System.out.print("수학총점:"+sumMat);
        System.out.printf(" 수학평균: %f\n",avgMat);
    }
}
