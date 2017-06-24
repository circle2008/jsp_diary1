package com.dlmu.circle.dao;

import com.dlmu.circle.model.Diary;
import com.dlmu.circle.model.pageBean;
import com.dlmu.circle.util.dateUtil;
import com.dlmu.circle.util.stringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cf on 2017/2/20.
 */
public class diaryDao {
    public List<Diary> diaryList(Connection con,pageBean pageBean,Diary s_diary) throws Exception {
        List<Diary> diaryList=new ArrayList<Diary>();
        StringBuffer sql=new StringBuffer("select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId");
        //搜索时进行模糊查询
        if(stringUtil.isNotEmpty(s_diary.getTitle())){
            sql.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
        }
        //首页日记类别查询
        if(s_diary.getTypeId()!=-1){
            sql.append(" and t1.typeId="+s_diary.getTypeId());
        }
        //首页日期类查询
        if(stringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
            sql.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
        }
        sql.append(" order by t1.releaseDate desc");
        //分页查询。根据pagestart和pagesize
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString());
        ResultSet rs=pstmt.executeQuery();
        while (rs.next()){
            Diary diary=new Diary();
            diary.setDiaryId(rs.getInt("diaryId"));
            diary.setTitle(rs.getString("title"));
            diary.setContent(rs.getString("content"));
            diary.setReleasedate(dateUtil.formatString(rs.getString("releaseDate"),"yyyy-MM-dd HH:mm:ss"));
            diaryList.add(diary);
        }
        return diaryList;
    }
    public int diaryCount(Connection con,Diary s_diary) throws SQLException {
        StringBuffer sql=new StringBuffer("select count(*) as total from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId");
        //搜索时进行模糊查询
        if(stringUtil.isNotEmpty(s_diary.getTitle())){
            sql.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
        }
        //首页日记类别查询
        if(s_diary.getTypeId()!=-1){
            sql.append(" and t1.typeId="+s_diary.getTypeId());
        }
        //首页日期类查询
        if(stringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
            sql.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString());
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            return rs.getInt("total");
        }else {
            return 0;
        }
    }
    public List<Diary> diaryCountByDate(Connection con) throws SQLException{
        List<Diary> diaryDateList=new ArrayList<Diary>();
        String sql="SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDateStr ,COUNT(*) AS diaryCount FROM t_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月') ORDER BY DATE_FORMAT(releaseDate,'%Y年%m月') DESC ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while (rs.next()){
            Diary diary=new Diary();
            diary.setReleaseDateStr(rs.getString("releaseDateStr"));
            diary.setDiaryDateCount(rs.getInt("diaryCount"));
            diaryDateList.add(diary);
        }
        return  diaryDateList;
    }
    public Diary diaryShow(Connection con,String diaryId) throws Exception {
        String sql="select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId and diaryId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diaryId);
        ResultSet rs=pstmt.executeQuery();
        Diary diary=new Diary();
        if(rs.next()){
            diary.setDiaryId(rs.getInt("diaryId"));
            diary.setTitle(rs.getString("title"));
            diary.setContent(rs.getString("content"));
            diary.setTypeId(rs.getInt("typeId"));
            diary.setTypeName(rs.getString("typeName"));
            diary.setReleasedate(dateUtil.formatString(rs.getString("releaseDate"),"yyyy-MM-dd HH:mm:ss"));
        }
        return diary;
    }
    public int diaryAdd(Connection con,Diary diary) throws SQLException {
        String sql="insert into t_diary values(null,?,?,?,now())";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diary.getTitle());
        pstmt.setString(2,diary.getContent());
        pstmt.setInt(3,diary.getTypeId());

        return pstmt.executeUpdate();
    }
    public int diaryDelete(Connection con,String diaryId) throws SQLException {
        String sql="delete from t_diary where diaryId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diaryId);
        return  pstmt.executeUpdate();

    }
    public int diaryModify(Connection con,Diary diary) throws SQLException{
        String sql="update t_diary set title=?,content=?,typeId=?,releaseDate=now() where diaryId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diary.getTitle());
        pstmt.setString(2,diary.getContent());
        pstmt.setInt(3,diary.getTypeId());
        pstmt.setInt(4,diary.getDiaryId());
        return pstmt.executeUpdate();

    }
    public int existDiary(Connection con,String diaryTypeId) throws SQLException {
        String sql="select count(*) as total from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId and diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diaryTypeId);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            return rs.getInt("total");
        }else {
            return 0;
        }
    }
}
