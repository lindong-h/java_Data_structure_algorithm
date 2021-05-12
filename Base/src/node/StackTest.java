package node;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @author dong
 * @create 2021-05-09 20:26
 */
// stack的基本使用
public class StackTest {
    public static void main(String[] args) {
        var stack = new Stack<String>();
        //入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        //出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());//pop() 就是将栈顶的数据取出
        }
    }
    @Test
    public void test(){
        System.out.println("git");
    }
}
