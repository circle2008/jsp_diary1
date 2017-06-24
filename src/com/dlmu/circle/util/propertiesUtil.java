package com.dlmu.circle.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cf on 2017/2/19.
 */
public class propertiesUtil {
    public static String getValue(String key){
        Properties prop=new Properties();
        InputStream in=new propertiesUtil().getClass().getResourceAsStream("/diary.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String)prop.get(key);
    }
}
