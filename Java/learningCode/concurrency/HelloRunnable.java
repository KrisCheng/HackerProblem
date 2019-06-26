package concurrency;

/**
 * Created by Kris Peng on 15:18 2019/6/20 .
 * All right reserved.
 */

public class HelloRunnable implements Runnable {

    public void run(){
        System.out.println("Hello from a thread.");
    }

    public static void main(String[] args) {
        (new Thread(new HelloRunnable())).start();
    }
}
