package designpattern.proxy.staticproxy;

/**
 * Created by Kris Peng on 09:42 2019/5/22 .
 * All right reserved.
 */
public class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println("模拟：保存用户！");
    }
    @Override
    public void find() {
        System.out.println("模拟：查询用户");
    }
}
