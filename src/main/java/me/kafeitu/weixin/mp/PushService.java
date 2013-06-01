package me.kafeitu.weixin.mp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明
 *
 * @author 程辉
 * @version V1.0  创建时间：2013-4-22 下午1:18:02
 */
public class PushService {

    public static String TOKEN;
    public static String LOGIN_URL = "http://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN";
    public static String SEND_MSG = "http://mp.weixin.qq.com/cgi-bin/singlesend?t=ajax-response&lang=zh_CN";

    /**
     * 获取登录session
     */
    public static Map<String, String> auth(String username, String password)
            throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("pwd", MD5.getMD5(password.getBytes())
                .toUpperCase());
        map.put("f", "json");
        Response response = Jsoup.connect(LOGIN_URL).ignoreContentType(true)
                .method(Method.POST).data(map).execute();
        Map<String, String> cookies = response.cookies();

        //新添加代码
        String json = response.body();
        System.out.println(json);
        Gson gson = new Gson();
        WeiXinResponse weiXinResponse = gson.fromJson(json, WeiXinResponse.class);
        String errMsg = weiXinResponse.getErrMsg();
        TOKEN = errMsg.substring(errMsg.lastIndexOf("=") + 1, errMsg.length());
        System.out.println("TOKEN=" + TOKEN);
        cookies = response.cookies();

        System.out.println(cookies);

        return cookies;
    }

    /**
     * 获取关注列表
     *
     * @param cookie
     * @return
     * @throws IOException
     */
    public static List<WeiXinFans> getFans(Map<String, String> cookie, String group)
            throws IOException {

        String FANS_URL = "http://mp.weixin.qq.com/cgi-bin/contactmanagepage?t=wxm-friend&token=" + TOKEN + "&lang=zh_CN&pagesize=10&pageidx=0&type=0&groupid=" + group;
        Document document = Jsoup.connect(FANS_URL).cookies(cookie).post();
        Elements eles = document.select("#json-friendList");
        Element element = eles.get(0);
        String json = element.data();
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<WeiXinFans>>() {
        }.getType());

    }

    /**
     * 发送消息
     */
    public static void sendMsg(Map<String, String> cookie, String content,
                               String fakeId) throws IOException {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tofakeid", fakeId);
        map.put("content", content);
        map.put("error", "false");
        map.put("token", TOKEN);
        map.put("type", "1");
        map.put("ajax", "1");
        String referrerUrl = "http://mp.weixin.qq.com/cgi-bin/singlemsgpage?token=" + TOKEN + "&fromfakeid=" + fakeId + "&msgid=&source=&count=20&t=wxm-singlechat&lang=zh_CN";
        Document document = Jsoup.connect(SEND_MSG).referrer(referrerUrl).data(map).cookies(cookie)
                .post();
        Element body = document.body();
        System.out.println(body.text());

    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

//        Map<String, String> cookie = auth("activiti.cn@gmail.com", "leilei");
        Map<String, String> cookie = auth("bill008@gmail.com", "802100");
        //获取粉丝列表
        List<WeiXinFans> list = getFans(cookie, "101");
        System.out.println(cookie);

        //群发
        for (WeiXinFans fans : list) {
            System.out.println(fans);
//			sendMsg(cookie, "起床了没有", fans.getFakeId());
        }

//		sendMsg(cookie, "通过java代码推送微信消息", "1011000680");

    }

}
