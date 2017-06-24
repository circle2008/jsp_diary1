package com.dlmu.circle.web;

import com.dlmu.circle.dao.userDao;
import com.dlmu.circle.model.User;
import com.dlmu.circle.util.DbUtil;
import com.dlmu.circle.util.dateUtil;
import com.dlmu.circle.util.propertiesUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cf on 2017/2/24.
 */
public class userServlet extends HttpServlet {
    DbUtil dbUtil=new DbUtil();
    userDao userDao=new userDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action=req.getParameter("action");
        if("preSave".equals(action)){
            this.userPreSave(req,resp);
        }else if ("save".equals(action)){
            this.userSave(req,resp);
        }


    }
    private void userPreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mainPage","user/userAdd.jsp");
        req.getRequestDispatcher("mainTemp.jsp").forward(req,resp);
    }
    private void userSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理包含图片各种信息的表单
        FileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        List<FileItem> items=null;
        try {
            items=upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> itr=items.iterator();
        HttpSession session=req.getSession();
        User user=(User) session.getAttribute("currentUser");
        boolean imageChange=false;
        while (itr.hasNext()){
            FileItem fileItem=(FileItem)itr.next();
            //如果是普通表单
            if(fileItem.isFormField()){
                String fieldName=fileItem.getFieldName();
                if("nickName".equals(fieldName)){
                    user.setNickName(fileItem.getString("utf-8"));
                }
                if("mood".equals(fieldName)){
                    user.setMood(fileItem.getString("utf-8"));
                }
            }else if (!"".equals(fileItem.getName())){
                try {
                    imageChange=true;
                    String imageName= dateUtil.getCurrentDateStr();
                    //图片文件命名+提取后缀
                    user.setImageName(imageName+"."+fileItem.getName().split("\\.")[1]);
                    //上传绝对路径
                    String uploadPath=propertiesUtil.getValue("imagePath")+imageName+"."+fileItem.getName().split("\\.")[1];
                    fileItem.write(new File(uploadPath));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(!imageChange){
                user.setImageName(user.getImageName().replaceFirst(propertiesUtil.getValue("imageFile"), ""));
            }
            Connection con=null;
            try {
                con=dbUtil.getCon();
                int saveNums=userDao.userUpdate(con,user);
                if(saveNums>0){
                    user.setImageName(propertiesUtil.getValue("imageFile")+user.getImageName());
                    session.setAttribute("currentUser",user);
                    req.getRequestDispatcher("main?all=true").forward(req,resp);
                }else {
                    req.setAttribute("currentUser",user);
                    req.setAttribute("error","个人信息保存不成功");
                    req.setAttribute("mainPage","user/userAdd.jsp");
                    req.getRequestDispatcher("mainTemp.jsp").forward(req,resp);
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
    }

}
