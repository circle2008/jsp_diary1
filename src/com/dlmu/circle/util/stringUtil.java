package com.dlmu.circle.util;

/**
 * Created by cf on 2017/2/20.
 */
public class stringUtil {
    public static boolean isEmpty(String str){
        if("".equals(str)||str==null){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isNotEmpty(String str){
        if(!"".equals(str)&&str!=null){
            return true;
        }else {
            return false;
        }
    }
}
