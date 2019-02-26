package servlets;

import bean.User;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.google.gson.Gson;
import db.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 登录servlet
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IOUtils.read(reader);
//        if (StringUtils.isNotBlank(body)) {
//            System.out.println("business receive somthing with body :" + body);
//        }

        User user = new Gson().fromJson(body, User.class);

        try (PrintWriter out = response.getWriter()) {

            //密码验证结果
            String UserNick = verifyLogin(user);

//            Map<String, String> params = new HashMap<>();
//            JSONObject jsonObject = new JSONObject();

            User retUser = new User();
            if (UserNick != null) {
//                params.put("Result", "success");
//                params.put("UserNick", userNick);
                retUser.setResult(User.Login_success);
                retUser.setUserNick(UserNick);
            } else {
                retUser.setResult(User.Login_failed);
            }
//            jsonObject.put("params", params);

            String retJson = new Gson().toJson(retUser, User.class);
            out.write(retJson);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 验证登录
     *
     * @param user
     * @return
     */
    private String verifyLogin(User user) {
        User retUser = UserDao.queryUser(user.getUserName());
        //账户密码验证
        if (null != user && user.getPassword().equals(retUser.getPassword())) {
            return retUser.getUserNick();
        }
        return null;
    }
}
