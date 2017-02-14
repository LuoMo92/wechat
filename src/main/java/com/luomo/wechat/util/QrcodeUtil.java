package com.luomo.wechat.util;

import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by LiuMei on 2017-02-14.
 * <p>
 * Java生成 二维码扫一扫工具类
 */
public class QrcodeUtil {

    /**
     * 生成二维码图像
     *
     * @param content 需要生成二维码的文字或者其他内容
     * @param imgPath 生成二维码图像保存的地址
     * @return void 无返回值
     */
    public static void QrcodeImg(String content, String imgPath) {

        int width = 140;
        int height = 140;
        try {
            //实例化Qrcode
            Qrcode qrcode = new Qrcode();
            //设置二维码排错率 可选L(7%),M(15%),Q(25%),H(30%)
            qrcode.setQrcodeErrorCorrect('M');
            qrcode.setQrcodeEncodeMode('B');

            //设置二维码尺寸 取值范围(1-40)
            qrcode.setQrcodeVersion(7);
            //设置图片尺寸
            BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //绘制二维码图片
            Graphics2D graphics = bufImg.createGraphics();
            //设置二维码背景颜色
            graphics.setBackground(Color.WHITE);
            //创建二维码矩形区域
            graphics.clearRect(0, 0, width, height);
            //设置二维码颜色
            graphics.setColor(Color.BLACK);

            int pixoff = 2;

            //获取内容的字节数组,设置编码集
            byte[] bytes = content.getBytes("utf-8");
            if (bytes.length > 0 && bytes.length < 120) {
                boolean[][] codeOut = qrcode.calQrcode(bytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            graphics.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                System.out.println("您输入的内容可能超过了最大限制值!");
            }
            //生成二维码图片
            File imgFile = new File(imgPath);
            ImageIO.write(bufImg, "png", imgFile);
            System.out.println("恭喜您,二维码生成成功!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Java入口
    public static void main(String[] args) {
        String content = "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=23&gp=0.jpg";
        String imgPath = "C:\\Users\\Administrator\\Desktop\\test.jpg";
        QrcodeImg(content, imgPath);
    }
}
