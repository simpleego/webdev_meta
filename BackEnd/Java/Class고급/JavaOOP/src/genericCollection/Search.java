package genericCollection;

import java.util.ArrayList;
import java.util.Collections;

public class Search {
    public static void main(String[] args) {
        int key = 9;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(12);
        list.add(50);
        list.add(50);
        list.add(50);
        list.add(70);
        System.out.println(list);
        int index = Collections.binarySearch(list,key);
        System.out.println("찾은 위치"+index);
    }
}
