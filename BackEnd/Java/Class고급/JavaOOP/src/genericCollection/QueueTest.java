package genericCollection;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {
    public static void main(String[] args) {
        int time = 10;
        Queue<Integer> queue = new LinkedList<>();

        // 대기행렬 생성
        for(int i=time; i>=0; i--){
            queue.add(i);
        }

        // 대기 행렬 처리
        while (!queue.isEmpty()){
            System.out.println(queue.remove()+" ");
            // 천천히 쉬면서 출력해, 식사마칠때 까지 대기(1초)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
