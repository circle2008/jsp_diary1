package com.dlmu.circle.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cf on 2017/2/20.
 */
public class dateUtil {
    public static String formatDate(Date date,String format){
        String result="";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        if(date!=null){
            result=sdf.format(date);
        }
        return result;
    }
    public static Date formatString(String str,String format) throws Exception{
        if(stringUtil.isEmpty(str)){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.parse(str);
    }
    //生成文件名
    public static String getCurrentDateStr()throws Exception{
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        return sdf.format(date);
    }

}
