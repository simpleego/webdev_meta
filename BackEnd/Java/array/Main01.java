package array;

public class Main01 {
    public static void main(String[] args) {

        if(args.length > 0){
            for (String arg : args){
                System.out.println("매개변수"+ arg);
            }

            if(args[0].equals("-h")){
                System.out.println("도움말입니다...");
            }
        }

    }
}
