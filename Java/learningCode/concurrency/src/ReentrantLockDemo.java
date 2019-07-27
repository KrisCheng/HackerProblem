import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Peng Cheng
 * @description:
 * @since: 2019/7/23 21:10
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock fairLock = new ReentrantLock(true);
        fairLock.lock();
        try {

        } finally {
            fairLock.unlock();
        }
    }
}
