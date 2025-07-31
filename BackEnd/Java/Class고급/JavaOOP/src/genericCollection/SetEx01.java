package genericCollection;

import java.util.HashSet;

public class SetEx01 {
    public static void main(String[] args) {
        String[] sample = {"단어","중복","구절","구문","중복"};
        HashSet<String> str = new HashSet<>();
        for (String s : sample){
            if(!str.add(s)){
                System.out.println("중복된 단어 :"+s);
            }
        }
        System.out.println(str.size() + "중복되지 않은 단어: "+str);
        System.out.println("중복된 단어의 수: "+(sample.length - str.size()));

    }
}
