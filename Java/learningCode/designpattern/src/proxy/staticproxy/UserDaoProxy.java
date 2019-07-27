package proxy.staticproxy;

/**
 * Created by Kris Peng on 09:44 2019/5/22 .
 * All right reserved.
 */

public class UserDaoProxy implements IUserDao {

    // 代理对象，需要维护一个目标对象
    private IUserDao target = new UserDao();

    @Override
    public void save() {
        System.out.println("代理操作： 开启事务...");
        target.save();   // 执行目标对象的方法
        System.out.println("代理操作：提交事务...");
    }

    @Override
    public void find() {
        target.find();
    }
}
