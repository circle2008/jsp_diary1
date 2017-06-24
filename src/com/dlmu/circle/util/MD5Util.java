package com.dlmu.circle.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cf on 2017/2/19.
 */
public class MD5Util {
    public static String EncoderPwdByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en=new BASE64Encoder();
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

}
