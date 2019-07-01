package singleton;

/**
 * @author: Peng Cheng
 * @description: 饿汉式单例 --> 指在方法调用前，实例就已经创建好了。
 * @since: 2019/6/28 21:36
 */
public class SingletonDemo1 {
    private static SingletonDemo1 instance = new SingletonDemo1();
    private SingletonDemo1() {}
    public static SingletonDemo1 getInstance() {
        return instance;
    }
}
