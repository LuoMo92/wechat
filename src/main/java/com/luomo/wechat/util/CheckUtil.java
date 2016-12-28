package com.luomo.wechat.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class CheckUtil {

    private static final String token = "luomo";

    public static boolean checkSignature(String signature,String timestamp,String nonce){

        String[] arr = new String[]{token,timestamp,nonce};

        //排序
        Arrays.sort(arr);

        //生成字符串
        StringBuffer content = new StringBuffer();
        for(int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }

        //SHA1加密
        String temp = getSHA1(content.toString());

        return temp.equals(signature);

    }

    public static String getSHA1(String str){
        if(str == null || str.length() == 0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] md = messageDigest.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for(int i=0;i<j;i++){
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
