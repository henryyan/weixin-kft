package me.kafeitu.weixin.web;

import me.kafeitu.common.util.properties.PropertyFileUtil;
import me.kafeitu.weixin.mp.beans.ImageMessage;
import me.kafeitu.weixin.mp.beans.MusicMessage;
import me.kafeitu.weixin.mp.beans.NewsItem;
import me.kafeitu.weixin.mp.beans.TextMessage;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 接入微信平台
 * User: henryyan
 */
@Controller
@RequestMapping("/service")
public class ServiceController extends ControllerSupport {

    private static final String RESPONSE_TXT = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType><Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";

    private static boolean checkSignature(String signature, String timestamp, String nonce) {
        String token = PropertyFileUtil.get("mp.token");
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    // 将字节转换为十六进制字符串
    private static String byteToHexStr(byte ib) {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F'
        };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];

        String s = new String(ob);
        return s;
    }

    // 将字节数组转换为十六进制字符串
    private static String byteToStr(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexStr(bytearray[i]);
        }
        return strDigest;
    }

    /**
     * 检查TOKEN，接入微信
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String checkToken(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                             @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        if (checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return StringUtils.EMPTY;
    }

    /**
     * 交互处理
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void reply(HttpServletRequest request, HttpServletResponse response) {

        Document doc = null;
        SAXReader reader = new SAXReader();
        try {
            PrintWriter out = response.getWriter();
            InputStream in = request.getInputStream();
            doc = reader.read(in);
            Element root = doc.getRootElement();
            String toUserName = root.element("ToUserName").getTextTrim();
            String fromUserName = root.element("FromUserName").getTextTrim();
            String msgType = root.element("MsgType").getTextTrim();
            logger.debug("msgType={}", msgType);
            if (StringUtils.equals(msgType, "text")) {
                String content = root.element("Content").getTextTrim();
                logger.debug("content={}", content);
                if (StringUtils.equals("博客", content )) {
                    ImageMessage message = new ImageMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());

                    NewsItem item = new NewsItem();
                    item.setTitle("用100行代码动态创建并部署流程");
                    item.setUrl("http://www.kafeitu.me/activiti/2013/05/27/dynamic-process-creation-and-deployment-in-100-lines.html");
                    item.setPicUrl("http://wechat.kafeitu.me/stores-4s/images/QQ20130313-1.png");
                    item.setDescription("用100行代码动态创建并部署流程--描述");
                    message.getItems().add(item);

                    item = new NewsItem();
                    item.setTitle("Activiti Explorer 5.11登录页面乱码解决办法");
                    item.setUrl("http://www.kafeitu.me/activiti/2012/12/15/activiti-explorer-5-11-garbled.html");
                    item.setPicUrl("http://www.kafeitu.me/files/2012/12/activiti-explorer-5.11-garbled.jpg");
                    item.setDescription("Activiti Explorer 5.11登录页面乱码解决办法--描述");
                    message.getItems().add(item);

                    logger.debug("输出博客={}", message.format());
                    message.print(out);
                } else if (StringUtils.equals("音乐", content)) {
                    MusicMessage message = new MusicMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());
                    message.setTitle("致青春");
                    message.setDescription("回味青春");
                    message.setMusicUrl("http://zhangmenshiting.baidu.com/data2/music/42911558/4015334072000192.mp3?xcode=244d7a1313eee1f39e0abe941821ec2a8f16d272034c7aaa");
                    message.setHQMusicUrl("http://zhangmenshiting.baidu.com/data2/music/42911558/4015334072000192.mp3?xcode=244d7a1313eee1f39e0abe941821ec2a8f16d272034c7aaa");
                    logger.debug("输出音乐={}", message.format());
                    message.print(out);
                } else if (StringUtils.equals("论坛", content)) {
                    TextMessage message = new TextMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());
                    message.setContent("论坛地址：http://www.activiti-cn.org");
                    logger.debug("输出文字={}", message.format());
                    message.print(out);
                } else if (StringUtils.equalsIgnoreCase("QQ", content)) {
                    TextMessage message = new TextMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());
                    message.setContent("我的QQ：576525789，欢迎骚扰！");
                    logger.debug("输出文字={}", message.format());
                    message.print(out);
                } else if (StringUtils.equalsIgnoreCase("微博", content)) {
                    TextMessage message = new TextMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());
                    message.setContent("我的微博：http://weibo.com/activiti @Activiti中文");
                    logger.debug("输出文字={}", message.format());
                    message.print(out);
                } else if (StringUtils.equalsIgnoreCase("QQ群", content)) {
                    TextMessage message = new TextMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());
                    message.setContent("236540304（已满），139983080");
                    logger.debug("输出文字={}", message.format());
                    message.print(out);
                } else {
                    TextMessage message = new TextMessage();
                    message.setToUserName(toUserName);
                    message.setFromUserName(fromUserName);
                    message.setCreateTime(System.currentTimeMillis());
                    message.setContent("您发送的内容是:【" + content + "】，输入下面的关键字可以获取不同信息：\n"
                    + "博客--获取www.kafeitu.me博客的博文\n"
                    + "音乐--直接播放音乐\n"
                    + "论坛--Activiti中文论坛地址\n"
                    + "微博--新浪微博账号\n"
                    + "QQ--咖啡兔的QQ号码\n"
                    + "QQ群--Activiti中文讨论群");
                    logger.debug("输出文字={}", message.format());
                    message.print(out);
                }
            } else if (StringUtils.equals(msgType, "location")) {
                String x = root.element("Location_X").getTextTrim();
                String y = root.element("Location_Y").getTextTrim();
                out.printf(RESPONSE_TXT, fromUserName, toUserName, System.currentTimeMillis(), "text", "已定位:X=" + x + ", Y=" + y);
            } else if (StringUtils.equals(msgType, "image")) {
                String picurl = root.element("PicUrl").getTextTrim();
                logger.debug("picture url: {}", picurl);

                // 构造URL
                URL url = new URL(picurl);
                // 打开连接
                URLConnection con = url.openConnection();
                // 输入流
                InputStream is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                OutputStream os = new FileOutputStream(PropertyFileUtil.get("receive.image.save.path") + "/" + System.currentTimeMillis());
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();

                out.printf(RESPONSE_TXT, fromUserName, toUserName, System.currentTimeMillis(), "link", picurl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
