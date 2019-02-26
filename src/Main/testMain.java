package Main;

import bean.User;
import db.dao.UserDao;

public class testMain {
    public static void main(String[] args) {
        User user = new User();
        user.setUserName("123");
        user.setPassword("456");
        user.setUserNick("789");
        System.out.println(UserDao.registerUser(user));
    }
}
