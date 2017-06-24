package com.dlmu.circle.web;

import com.dlmu.circle.dao.diaryDao;
import com.dlmu.circle.dao.diaryTypeDao;
import com.dlmu.circle.model.diaryType;
import com.dlmu.circle.util.DbUtil;
import com.dlmu.circle.util.stringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by cf on 2017/2/23.
 */
public class diaryTypeServlet extends HttpServlet {
    DbUtil dbUtil=new DbUtil();
    diaryTypeDao diaryTypeDao=new diaryTypeDao();
    diaryDao diaryDao=new diaryDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");
        String action=req.getParameter("action");
        if("List".equals(action)){
            this.diaryTypeList(req,resp);
        }else if("preSave".equals(action)){
            this.diaryTypePreSave(req,resp);
        }else if("save".equals(action)){
            this.diaryTypeSave(req,resp);
        }else if("delete".equals(action)){
            this.diaryTypeDelete(req,resp);
        }
    }
    private void diaryTypeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection con=null;
        try {
            con=dbUtil.getCon();
            req.setAttribute("diaryTypeList",diaryTypeDao.diaryTypeList(con));
            req.setAttribute("mainPage","diaryType/diaryTypeList.jsp");
            req.getRequestDispatcher("mainTemp.jsp").forward(req,resp);
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
    private void diaryTypePreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String dairyTypeId=req.getParameter("diaryTypeId");
        Connection con=null;
        try {
            if(stringUtil.isNotEmpty(dairyTypeId)){
                con=dbUtil.getCon();
                diaryType diaryType=diaryTypeDao.diaryTypeShow(con,dairyTypeId);
                req.setAttribute("diaryType",diaryType);
            }
            req.setAttribute("mainPage","diaryType/diaryTypeAdd.jsp");
            req.getRequestDispatcher("mainTemp.jsp").forward(req,resp);
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
    private void diaryTypeSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String typeName=req.getParameter("typeName");
        String diaryTypeId=req.getParameter("diaryTypeId");
        diaryType diaryType=new diaryType();
        diaryType.setTypeName(typeName);
        Connection con=null;
        if(stringUtil.isNotEmpty(diaryTypeId)){
            diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
        }
        try {
            con=dbUtil.getCon();
            int saveNum;
            if(stringUtil.isNotEmpty(diaryTypeId)){
                saveNum=diaryTypeDao.diaryUpdate(con,diaryType);
            }else {
                saveNum=diaryTypeDao.diaryTypeAdd(con,diaryType);
            }
            if(saveNum>0){
                req.getRequestDispatcher("diaryType?action=List").forward(req,resp);
            }else {
                req.setAttribute("diaryType",diaryType);
                req.setAttribute("error","添加类别失败");
                req.setAttribute("mainPage","diaryType/diaryTypeAdd.jsp");
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
    private void diaryTypeDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String diaryTypeId=req.getParameter("diaryTypeId");
        Connection con=null;
        try {
            con=dbUtil.getCon();
            int count=diaryDao.existDiary(con,diaryTypeId);
            if(count>0){
                req.setAttribute("error","该类别下有日志不能删除！");
            }else {
                diaryTypeDao.diaryTypeDelete(con,diaryTypeId);
            }
            req.getRequestDispatcher("diaryType?action=List").forward(req,resp);
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
