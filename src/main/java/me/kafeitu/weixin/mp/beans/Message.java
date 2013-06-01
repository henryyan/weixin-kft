package me.kafeitu.weixin.mp.beans;

import java.io.PrintWriter;

/**
 * User: henryyan
 */
public abstract class Message {

    protected String ToUserName; // 接收方帐号（收到的OpenID）
    protected String FromUserName; // 开发者微信号
    protected long CreateTime; // 消息创建时间

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public abstract void print(PrintWriter out);
}
