package genericCollection;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorEx {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        list.add("하나");
        list.add("둘");
        list.add("셋");
        list.add("넷");
        //String str;

        for (String str : list){
            System.out.println(str);
        }

//        Iterator<String> e = list.iterator();
//        while (e.hasNext()){
//            str = (String) e.next();
//            System.out.println(str);
//        }

    }
}
