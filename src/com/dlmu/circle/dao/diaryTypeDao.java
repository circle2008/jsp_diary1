package com.dlmu.circle.dao;

import com.dlmu.circle.model.diaryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by cf on 2017/2/21.
 */
public class diaryTypeDao {
    public List<diaryType> diaryTypeCount(Connection con) throws SQLException {
        List<diaryType> diaryTypeList=new ArrayList<diaryType>();
        String sql="select diaryTypeId,typeName,count(diaryId) as diaryTypeCount from t_diary RIGHT JOIN t_diaryType on t_diary.typeId=t_diaryType.diaryTypeId group by typeName";
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while (rs.next()){
            diaryType diaryType=new diaryType();
            diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
            diaryType.setDiaryCount(rs.getInt("diaryTypeCount"));
            diaryTypeList.add(diaryType);
        }
        return  diaryTypeList;
    }
    public List<diaryType> diaryTypeList(Connection con) throws SQLException{
        List<diaryType> diaryTypeList=new ArrayList<diaryType>();
        String sql="select * from t_diaryType";
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while (rs.next()){
            diaryType diaryType=new diaryType();
            diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
            diaryTypeList.add(diaryType);
        }
        return diaryTypeList;
    }
    public int diaryTypeAdd(Connection con,diaryType diaryType)throws SQLException{
        String sql="insert into t_diaryType values(null,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diaryType.getTypeName());
        return pstmt.executeUpdate();
    }
    public int diaryUpdate(Connection con,diaryType diaryType) throws SQLException{
        String sql="update t_diaryType set typeName=? where diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diaryType.getTypeName());
        pstmt.setInt(2,diaryType.getDiaryTypeId());
        return pstmt.executeUpdate();
    }
    public diaryType diaryTypeShow(Connection con,String diaryTypeId) throws SQLException{
        String sql="select * from t_diaryType where diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,diaryTypeId);
        ResultSet rs=pstmt.executeQuery();
        diaryType diaryType=new diaryType();
        if (rs.next()){
            diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
        }
        return diaryType;
    }
    public int diaryTypeDelete(Connection con,String diaryTypeId) throws SQLException{
        String sql="delete from t_diaryType where diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, diaryTypeId);
        return pstmt.executeUpdate();
    }

}
