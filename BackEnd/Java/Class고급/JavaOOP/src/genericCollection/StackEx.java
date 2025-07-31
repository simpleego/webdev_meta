package genericCollection;

import java.util.Stack;

public class StackEx {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.add("20");
        stack.add("50");
        stack.add("80");
        stack.add("70");

        System.out.println(stack);
        System.out.println("Peek:"+stack.peek());
        System.out.println("pop:"+stack.pop());
       stack.push("90");
        System.out.println(stack);
        int i = stack.search("20");
        System.out.println("i:"+i);
        System.out.println(stack.size());
        stack.remove(0);
        System.out.println(stack);

    }
}
