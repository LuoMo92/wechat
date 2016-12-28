package com.luomo.wechat.servlet;

import com.luomo.wechat.util.CheckUtil;
import com.luomo.wechat.util.MessageUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class WechatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            out.write(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            Map<String ,String> map = MessageUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String createTime = map.get("CreateTime");
            String content = map.get("Content");
            String msgType = map.get("MsgType");
            String msgId = map.get("MsgId");

            String message = null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){

                if("1".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());
                }else if("2".equals(content)){
                    message = MessageUtil.initNews(toUserName,fromUserName);
                }else {
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }
            out.write(message);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
