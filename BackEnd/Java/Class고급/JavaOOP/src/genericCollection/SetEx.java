package genericCollection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class SetEx {
    public static void main(String[] args) {

        HashSet<String> str = new HashSet<>();
        str.add("사과");
        str.add("배");
        str.add("바나나");
        str.add("사과");
        System.out.println(str);
//        System.out.println(str.size());
//        str.add("귤");
//        str.add("딸기");
//        System.out.println(str);
//        System.out.println(str.size());
        //str.remove("사과");
//        str.set(0,"키위");
        System.out.println(str);
        System.out.println(str.contains("사과"));
        System.out.println(str.size());
        // 사과를 apple로 수정
        // 기존 아이템을 제거하고 새로운 아이템 추가
        str.remove("사과");
        str.add("apple");
        System.out.println(str);
//
//        Scanner kbd = new Scanner(System.in);
//        System.out.println("찾고싶은 과일은 ?");
//        String fruit = kbd.nextLine();
//
//        boolean isContain = str.contains(fruit);
//        if (isContain) {
//            System.out.println(fruit + "가 있다.");
//        } else {
//            System.out.println(fruit + "가 없다.");
//        }
//
//        // ArrayList에서 값을 꺼낸다.
//        for (int i = 0; i < str.size(); i++) {
//            String fruit1 = str.get(i);
//            if (fruit1.equals(fruit)) {
//                System.out.println(fruit + "은 " + i + "번째에 있습니다.");
//            }
//        }
    }
}
