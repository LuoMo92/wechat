package com.luomo.wechat.util;

import com.luomo.wechat.po.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class MessageUtil {

    public static final String URL = "http://luomo.tunnel.qydev.com/wechat";

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE= "image";
    public static final String MESSAGE_NEWS= "news";
    public static final String MESSAGE_VOICE= "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION= "location";
    public static final String MESSAGE_EVENT= "event";
    public static final String MESSAGE_SUBSCRIBE= "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE= "unsubscribe";
    public static final String MESSAGE_CLICK= "CLICK";
    public static final String MESSAGE_VIEW= "VIEW";
    public static final String MESSAGE_SCAN= "scancode_push";

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

    /**
     * 拼接文本消息
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setContent(content);
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

    /**
     * 图文消息转为xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item", new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     *图文消息组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNews(String toUserName,String fromUserName){
        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setTitle("测试公众号");
        news.setDescription("扫描图片即可关注测试公众号");
        news.setPicUrl(URL+"/image/测试公众号二维码.png");
        news.setUrl("www.baidu.com");
        newsList.add(news);

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        return newsMessageToXml(newsMessage);
    }

    /**
     * 组装图片消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImage(String toUserName,String fromUserName){

        Image image = new Image();
        image.setMediaId("ihUcoDIDDxpBlegy1zR1nBVFexZMt3TsyPF7Na_obuJ2StSd1G0S6_JUhPa3k4qx");

        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setImage(image);
        return imageMessageToXml(imageMessage);
    }

    /**
     * 图片消息转为xml
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xStream = new XStream();
        xStream.alias("xml",imageMessage.getClass());
        xStream.alias("Image", new News().getClass());
        return xStream.toXML(imageMessage);
    }

}
