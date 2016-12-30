package com.luomo.wechat.servlet;

import com.luomo.wechat.util.CheckUtil;
import com.luomo.wechat.util.MessageUtil;
import com.luomo.wechat.util.WechatUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
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
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.write(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                if("1".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                }else if("2".equals(content)){
                    message = MessageUtil.initNews(toUserName, fromUserName);
                }else if("3".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
                }else if("?".equals(content) || "？".equals(content)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if(content.startsWith("翻译")){
                    String word = content.replaceAll("^翻译", "").trim();
                    if("".equals(word)){
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
                    }else{
                        message = MessageUtil.initText(toUserName, fromUserName, WechatUtil.trans(word,"auto","auto"));
                    }
                }
            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, url);
                }else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
                    String key = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, key);
                }
            }else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
                String label = map.get("Label");
                message = MessageUtil.initText(toUserName, fromUserName, label);
            }

            System.out.println(message);

            out.print(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            out.close();
        }
    }
}
