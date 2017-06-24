package com.dlmu.circle.web;

import com.dlmu.circle.dao.userDao;
import com.dlmu.circle.model.User;
import com.dlmu.circle.util.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by cf on 2017/2/18.
 */
public class loginServlet extends HttpServlet {
    DbUtil dbUtil=new DbUtil();
    userDao userDao=new userDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session=req.getSession();
        String userName=req.getParameter("userName");
        String password=req.getParameter("password");
        String remember=req.getParameter("remember");
        Connection con=null;
        try {
           con=dbUtil.getCon();
            User user=new User(userName,password);
            User currentUser=userDao.login(con,user);
            if(currentUser==null){
                req.setAttribute("user",user);
                req.setAttribute("error","用户名或密码错误");
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }else{
                if("remember-me".equals(remember)){
                    rememberMe(userName,password,resp);
                }
                session.setAttribute("currentUser",currentUser);
                req.getRequestDispatcher("main").forward(req,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void rememberMe(String userName,String password,HttpServletResponse resp){
        Cookie user=new Cookie("user",userName+"-"+password);
        user.setMaxAge(1*60*60*24*7);
        resp.addCookie(user);
    }
}
