package concurrency;

/**
 * Created by Kris Peng on 15:29 2019/6/20 .
 * All right reserved.
 */

public class HelloThread extends Thread{

    public void run(){
        System.out.println("Hello from a thread.");
    }

    public static void main(String[] args) {
        (new HelloThread()).start();
    }
}
