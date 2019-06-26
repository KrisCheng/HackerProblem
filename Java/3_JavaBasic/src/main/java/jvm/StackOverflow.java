package jvm;

/**
 * Created by Kris Peng on 09:55 2019/5/8 .
 * All right reserved.
 */
public class StackOverflow {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        StackOverflow oom = new StackOverflow();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
