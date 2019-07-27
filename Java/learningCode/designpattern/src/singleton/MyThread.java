package singleton;

/**
 * @author: Peng Cheng
 * @description: 测试类
 * @since: 2019/7/1 19:13
 */
public class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println(SingletonDemo2.getInstance().hashCode());
    }

    public static void main(String[] args) {
        MyThread[] mts = new MyThread[10];
        for(int i = 0; i < mts.length; i++) {
            mts[i] = new MyThread();
        }
        for(int i = 0; i < mts.length; i++) {
            mts[i].start();
        }
    }
}
