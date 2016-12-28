package com.luomo.wechat.test;

import com.luomo.wechat.po.AccessToken;
import com.luomo.wechat.util.WechatUtil;

import java.io.IOException;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class WechatTest {

    public static void main(String[] args) {
        AccessToken token = WechatUtil.getAccessToken();
        System.out.println("token:"+token.getToken());
        System.out.println("有效时间:"+token.getExpiresIn());

        String path = "C:\\Users\\Administrator\\Pictures\\tup-island.jpg";
        try {
            String  mediaId = WechatUtil.upload(path,token.getToken(),"image");
            System.out.println(mediaId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
