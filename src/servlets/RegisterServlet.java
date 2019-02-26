package servlets;

import bean.User;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.google.gson.Gson;
import db.DBManager;
import db.dao.UserDao;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IOUtils.read(reader);
        if (StringUtils.isNotBlank(body)) {
            System.out.println("business receive somthing with body :" + body);
        }

        User user = new Gson().fromJson(body, User.class);

        try (PrintWriter out = response.getWriter()) {

            User retUser = new User();

            if (verifyRegister(user)) {
                retUser.setResult(User.Register_success);
            } else {
                retUser.setResult(User.Register_failed);
            }

            String retJson = new Gson().toJson(retUser, User.class);
            out.write(retJson);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private boolean verifyRegister(User user) {
        return UserDao.registerUser(user);
    }
}

