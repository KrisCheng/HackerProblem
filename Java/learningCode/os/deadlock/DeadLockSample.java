package deadlock;

/**
 * Created by Kris Peng on 23:49 2019/6/27 .
 * All right reserved.
 */
public class DeadLockSample extends Thread {
    private String first;
    private String second;

    public DeadLockSample(String name, String first, String second){
        super(name);
        this.first = first;
        this.second = second;
    }
    public void run(){
        synchronized (first){
            System.out.println(this.getName() + " obtained: " + first);
            try{
                Thread.sleep(1000L);
                synchronized (second){
                    System.out.println(this.getName() + " obtained: " + second);
                }
            }catch (InterruptedException e){
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
        DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
