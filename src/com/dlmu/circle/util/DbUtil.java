package com.dlmu.circle.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by cf on 2017/2/18.
 */
public class DbUtil {

    public Connection getCon() throws Exception{
        Class.forName(propertiesUtil.getValue("jdbcName"));
        Connection con= DriverManager.getConnection(propertiesUtil.getValue("dbUrl"),propertiesUtil.getValue("dbUserName"),propertiesUtil.getValue("password"));
        return con;
    }
    public void closeCon(Connection con)throws Exception{
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        DbUtil dbUtil=new DbUtil();
        try {
            dbUtil.getCon();
            System.out.print("连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
