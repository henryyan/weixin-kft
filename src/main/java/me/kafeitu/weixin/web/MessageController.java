package me.kafeitu.weixin.web;

import me.kafeitu.common.util.properties.PropertyFileUtil;
import me.kafeitu.weixin.mp.PushService;
import me.kafeitu.weixin.mp.WeiXinFans;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 消息推送控制器
 * User: henryyan
 */
@Controller
@RequestMapping("/service")
public class MessageController extends ControllerSupport {

    /**
     * 消息推送
     *
     * @param group
     */
    @RequestMapping(value = "push")
    @ResponseBody
    public Boolean push(@RequestParam("group") String group, @RequestParam("content") String content) {
        Map<String, String> cookie = null;
        try {
            String username = PropertyFileUtil.get("mp.username");
            String password = PropertyFileUtil.get("mp.password");
            String token = PropertyFileUtil.get("mp.token");
            logger.debug("username={}", username);
            logger.debug("password=", password);
            logger.debug("token=", token);
            cookie = PushService.auth(username, password);
            //获取粉丝列表
            List<WeiXinFans> list = PushService.getFans(cookie, group);

            for (WeiXinFans fans : list) {
                System.out.println(fans);
                PushService.sendMsg(cookie, content, fans.getFakeId());
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return true;
    }

}
