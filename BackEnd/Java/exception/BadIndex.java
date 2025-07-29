package exception;

public class BadIndex {
    public static void main(String[] args) {
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = 0;
        }
        try {
            int result = array[12];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("배열 범위를 체크해 보세요.");
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println("배열에 관한 에러가 발생했습니다.");
        }
        System.out.println("과연 이 문장이 실행될까?");
    }
}
