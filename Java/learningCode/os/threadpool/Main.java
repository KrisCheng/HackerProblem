package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kris Peng on 14:01 2019/6/27 .
 * All right reserved.
 */
public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
        for(int i = 0; i < 15; i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}

class MyTask implements Runnable {
    private int taskNum;
    public MyTask(int num) {
        this.taskNum = num;
    }
    @Override
    public void run() {
        System.out.println("正在执行task: " + taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---");
        System.out.println("task " + taskNum + " 执行完毕");
    }
}

