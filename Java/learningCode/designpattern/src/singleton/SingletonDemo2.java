package singleton;

/**
 * @author: Peng Cheng
 * @description: 懒汉式单例 --> 指在方法调用获取实例时才创建实例。(以下为 双重检查锁 版本)
 * @since: 2019/7/1 19:17
 */
public class SingletonDemo2 {

    private volatile static SingletonDemo2 instance = null;

    private SingletonDemo2() {}

    public static SingletonDemo2 getInstance() {
//        if(instance == null) {
//            instance = new SingletonDemo2();
//        }
//        return instance;
        try {
            if (instance != null) {

            } else {
                Thread.sleep(1000);
                synchronized (SingletonDemo2.class) {
                    if (instance == null) {
                        instance = new SingletonDemo2();
                    }
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
