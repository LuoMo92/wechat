package com.luomo.wechat.test;

import com.luomo.wechat.po.AccessToken;
import com.luomo.wechat.util.WechatUtil;
import net.sf.json.JSONObject;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class WechatTest {

    public static void main(String[] args) {

        try {
            AccessToken token = WechatUtil.getAccessToken();
            System.out.println("token:"+token.getToken());
            System.out.println("有效时间:"+token.getExpiresIn());

//            String path = "C:\\Users\\Administrator\\Pictures\\tup-island.jpg";
//            String  mediaId = WechatUtil.upload(path,token.getToken(),"image");
//            System.out.println(mediaId);

            String menu = JSONObject.fromObject(WechatUtil.initMenu()).toString();
            int result = WechatUtil.createMenu(token.getToken(),menu);
            if(result == 0){
                System.out.println("创建菜单成功!");
            }else {
                System.out.println("errcode:"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
