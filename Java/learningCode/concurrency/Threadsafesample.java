package concurrency;

/**
 * Created by Kris Peng on 11:32 2019/6/24 .
 * All right reserved.
 */

public class Threadsafesample {
    public int sharedState;
    public void nonSafeAction(){
//        synchronized (this) {
        while(sharedState < 1000000){
            int former = sharedState++;
            int latter = sharedState;
            if(former != latter - 1){
                System.out.println("Observed data race. former is " + former + " , latter is "
                + latter);
            }
        }
//        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Threadsafesample threadsafesample = new Threadsafesample();
        Thread threadA = new Thread(){
            public void run(){
                threadsafesample.nonSafeAction();
            }
        };
        Thread threadB = new Thread(){
            public void run(){
                threadsafesample.nonSafeAction();
            }
        };
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
    }
}
