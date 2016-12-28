package com.luomo.wechat.util;

import com.luomo.wechat.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE= "image";
    public static final String MESSAGE_VOICE= "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION= "location";
    public static final String MESSAGE_EVENT= "event";
    public static final String MESSAGE_SUBSCRIBE= "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE= "unsubscribe";
    public static final String MESSAGE_CLICK= "CLICK";
    public static final String MESSAGE_VIEW= "VIEW";

    /**
     * xml转成map
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        ServletInputStream inputStream = request.getInputStream();
        Document doc = reader.read(inputStream);
        Element root = doc.getRootElement();
        List<Element> elements = root.elements();
        for(Element e : elements){
            map.put(e.getName(),e.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * 将文本消息对象转为xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setContent("您发送的消息是:"+content);
        return textMessageToXml(textMessage);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("欢迎您的关注,请按照菜单提示进行操作:\n\n");
        buffer.append("1.请回复1\n");
        buffer.append("2.请回复2\n");
        return buffer.toString();
    }

    /**
     * 菜单1
     * @return
     */
    public static String firstMenu(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("恭喜你\n\n");
        buffer.append("成功得到菜单1的回复\n");
        buffer.append("请继续加油啊~\n");
        return buffer.toString();
    }

    /**
     * 菜单2
     * @return
     */
    public static String secondMenu(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("恭喜你\n\n");
        buffer.append("成功得到菜单2的回复\n");
        buffer.append("请继续加油啊~\n");
        return buffer.toString();
    }
}
