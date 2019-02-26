package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 数据库管理类，提供连接数据库和拆解链接功能
 *
 * @author Implementist
 */
public class DBManager extends HttpServlet {

    //定义一个ServletConfig对象
    ServletConfig config;
    //定义的数据库用户名
    private static String username;
    //定义的数据库连接密码
    private static String password;
    //定义数据库连接URL
    private static String url;
    //定义连接
    private static Connection connection;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //继承父类的init()方法
        super.init(config);
        //获取配置信息
        this.config = config;
        //获取数据库用户名
        username = config.getInitParameter("DBUsername");
        //获取数据库连接密码
        password = config.getInitParameter("DBPassword");
        //获取数据库连接URL
        url = config.getInitParameter("ConnectionURL");
    }

    /**
     * 获得数据库连接对象
     *
     * @return 数据库连接对象
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    /**
     * 关闭所有的数据库连接资源
     *
     * @param connection Connection 链接
     * @param statement  Statement 资源
     * @param resultSet  ResultSet 结果集合
     */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}