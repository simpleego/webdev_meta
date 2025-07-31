package genericCollection;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriQueueTest {
    public static void main(String[] args) {
        int time = 10;
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.add(30);
        pq.add(80);
        pq.add(20);

        // 대기행렬 생성
        for(Integer o : pq){
            System.out.println(o);
        }

        // 대기 행렬 처리
        System.out.println("원소 삭제(서비스 처리)");
        while (!pq.isEmpty()){
            System.out.println(pq.remove());
        }

    }
}
