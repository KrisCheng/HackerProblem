package proxy.staticproxy;

/**
 * Created by Kris Peng on 09:44 2019/5/22 .
 * All right reserved.
 */
public class Main {
    public static void main(String[] args) {
        IUserDao proxy = new UserDaoProxy();
        proxy.save();
    }
}
