package org.example.utils;

/**
 * @author Keat-Jie
 * @version 1.0
 * @date 2023/2/21
 */

import org.example.handler.exceptionhandler.JavaWebException;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.UUID;

public class Md5Utils {


    /**
     * 生成盐
     * @return
     */
    public static String createSalt(){
        return  UUID.randomUUID().toString();
    }

    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String md5Password(String password,String salt){

        try {
            //1.获取MD5加密对象
            MessageDigest messageDigest = MessageDigest.getInstance("md5");

            byte[] digest = messageDigest.digest((password + salt).getBytes());


            //BAS464编码
            BASE64Encoder encoder = new BASE64Encoder();

            String md5Password = encoder.encode(digest);

            return md5Password;
        }catch (Exception e){
            e.printStackTrace();
            throw new JavaWebException(20001,"加密失败!");
        }
    }

}

