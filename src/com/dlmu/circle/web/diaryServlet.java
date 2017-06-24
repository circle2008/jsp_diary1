package com.dlmu.circle.web;

import com.dlmu.circle.dao.diaryDao;
import com.dlmu.circle.model.Diary;
import com.dlmu.circle.util.DbUtil;
import com.dlmu.circle.util.stringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by cf on 2017/2/22.
 */
public class diaryServlet extends HttpServlet {
    DbUtil dbUtil=new DbUtil();
    diaryDao diaryDao=new diaryDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setCharacterEncoding("utf-8");
        //日志有查看，删除和修改的操作，所以对应不同的action
            String action=req.getParameter("action");

            if("show".equals(action)){
                this.diaryShow(req,resp);
            }else if ("preSave".equals(action)){
                this.diaryPreSave(req,resp);
            }else if("save".equals(action)){
                this.diarySave(req,resp);
            }else if("delete".equals(action)){
                this.diaryDelete(req,resp);
            }
    }
    private void diaryShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String diaryId=req.getParameter("diaryId");
        Connection con=null;
        try {
            con=dbUtil.getCon();
            Diary diary=diaryDao.diaryShow(con,diaryId);
            req.setAttribute("diary",diary);
            req.setAttribute("mainPage","diary/diaryShow.jsp");
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
    private void diaryPreSave(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String diaryId=req.getParameter("diaryId");
        Connection con=null;
        try {
            //每次点击日志添加时先进行判断是添加还是修改，跳转到同一diaryAdd页面
            if(stringUtil.isNotEmpty(diaryId)){
                con=dbUtil.getCon();
                Diary diary=diaryDao.diaryShow(con,diaryId);
                req.setAttribute("diary",diary);
            }
            req.setAttribute("mainPage","diary/diaryAdd.jsp");
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
    private void diarySave(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String typeId=req.getParameter("typeId");
        String diaryId=req.getParameter("diaryId");
        Diary diary=new Diary(title,content,Integer.parseInt(typeId));
        if(stringUtil.isNotEmpty(diaryId)){
            diary.setDiaryId(Integer.parseInt(diaryId));
        }
        Connection con=null;
        try {
            con=dbUtil.getCon();
            int saveNum;
            //如果diaryId不为空,则调用update
            if(stringUtil.isNotEmpty(diaryId)){
                saveNum=diaryDao.diaryModify(con,diary);
            }else {
                saveNum=diaryDao.diaryAdd(con,diary);
            }
            if(saveNum>0){
                req.getRequestDispatcher("main?all=true").forward(req,resp);
            }else {
                req.setAttribute("diary",diary);
                req.setAttribute("error","保存失败");
                req.setAttribute("mainPage","diary/diaryAdd.jsp");
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
    private void diaryDelete(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String diaryId=req.getParameter("diaryId");
        Connection con=null;
        try {
            con=dbUtil.getCon();
            diaryDao.diaryDelete(con,diaryId);
            req.getRequestDispatcher("main?all=true").forward(req,resp);
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
