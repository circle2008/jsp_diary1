package com.dlmu.circle.web;

import com.dlmu.circle.dao.diaryDao;
import com.dlmu.circle.dao.diaryTypeDao;
import com.dlmu.circle.model.Diary;
import com.dlmu.circle.model.pageBean;
import com.dlmu.circle.util.DbUtil;
import com.dlmu.circle.util.propertiesUtil;
import com.dlmu.circle.util.stringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
/**
 * Created by cf on 2017/2/20.
 */
public class mainServlet extends HttpServlet {
    DbUtil dbUtil=new DbUtil();
    diaryDao diaryDao=new diaryDao();
    diaryTypeDao diaryTypeDao=new diaryTypeDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");

        Connection con=null;
        HttpSession session=req.getSession();
        //分页时提供的参数main?page=
        String page=req.getParameter("page");
        //按日期和类别分类时提供的参数
        String s_typeId=req.getParameter("s_typeId");
        String s_releaseDateStr=req.getParameter("s_releaseDateStr");
        //搜索时提供的参数
        String all=req.getParameter("all");
        String s_title=req.getParameter("s_title");
        Diary diary=new Diary();
        //写查询,如果查询则执行if操作
        if("true".equals(all)){
            if(stringUtil.isNotEmpty(s_title));
            diary.setTitle(s_title);
            session.removeAttribute("s_typeId");
            session.removeAttribute("s_releaseDateStr");
            session.setAttribute("s_title",s_title);

        }else {
            if(stringUtil.isNotEmpty(s_typeId)){
                diary.setTypeId(Integer.parseInt(s_typeId));
                session.setAttribute("s_typeId",s_typeId);
                //按类别和日期分类，二者只能选其一
                session.removeAttribute("s_releaseDateStr");
                //s_title.s_typeId,s_releaseDateStr三者互为排斥关系
                session.removeAttribute("s_title");
            }
            if(stringUtil.isNotEmpty(s_releaseDateStr)){
                s_releaseDateStr=new String(s_releaseDateStr.getBytes("ISO-8859-1"),"UTF-8");
                diary.setReleaseDateStr(s_releaseDateStr);
                session.setAttribute("s_releaseDateStr",s_releaseDateStr);
                //按类别和日期分类，二者只能选其一
                session.removeAttribute("s_typeId");
                session.removeAttribute("s_title");
            }
            if(stringUtil.isEmpty(s_typeId)){
                Object o=session.getAttribute("s_typeId");
                if(o!=null){
                    diary.setTypeId(Integer.parseInt((String)o));
                }

            }
            if(stringUtil.isEmpty(s_releaseDateStr)){
                Object o=session.getAttribute("s_releaseDateStr");
                if(o!=null){
                    diary.setReleaseDateStr((String)o);
                }
            }
            if(stringUtil.isEmpty(s_title)){
                Object o=session.getAttribute("s_title");
                if(o!=null){
                    diary.setTitle((String)o);
                }
            }
        }
        if(stringUtil.isEmpty(page)){
            page="1";
        }

        try {
            con=dbUtil.getCon();
            pageBean pagebean=new pageBean(Integer.parseInt(page),Integer.parseInt(propertiesUtil.getValue("pageSize")));
            List<Diary> diaryList=diaryDao.diaryList(con,pagebean,diary);

            int total=diaryDao.diaryCount(con,diary);
            String pageCode=this.getPagination(total,Integer.parseInt(propertiesUtil.getValue("pageSize")),Integer.parseInt(page));
            req.setAttribute("diaryList",diaryList);
            req.setAttribute("pageCode",pageCode);
            req.setAttribute("title",s_title);
            session.setAttribute("typeCountList",diaryTypeDao.diaryTypeCount(con));
            session.setAttribute("diaryDateCount",diaryDao.diaryCountByDate(con));
            req.setAttribute("mainPage","diary/diaryList.jsp");
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
    private String getPagination(int totalNum,int pageSize,int currentPage){
        int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode=new StringBuffer();
        pageCode.append("<li><a href='main?page=1'>首页</a></li>");
        if(currentPage==1){
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else{
            pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        for(int i=currentPage-2;i<=currentPage+2;i++){
            if(i<1||i>totalPage){
                continue;
            }
            if(i==currentPage){
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            }else{
                pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
            }
        }
        if(currentPage==totalPage){
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }else{
            pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");
        return pageCode.toString();
    }
}
