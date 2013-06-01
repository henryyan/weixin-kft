package me.kafeitu.weixin.mp.beans;

import java.io.PrintWriter;

/**
 * User: henryyan
 */
public class MusicMessage extends Message {

    private String Title;
    private String Description;
    private String MusicUrl; // 音乐链接
    private String HQMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐

    public static final String TEMPLATE = "<xml>" +
            "<ToUserName><![CDATA[%s]]></ToUserName>" +
            "<FromUserName><![CDATA[%s]]></FromUserName>" +
            "<CreateTime>%s</CreateTime>" +
            "<MsgType><![CDATA[music]]></MsgType>" +
            "<Music>" +
            "<Title><![CDATA[%s]]></Title>" +
            "<Description><![CDATA[%s]]></Description>" +
            "<MusicUrl><![CDATA[%s]]></MusicUrl>" +
            "<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>" +
            "</Music>" +
            "<FuncFlag>0</FuncFlag>" +
            "</xml>";

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public void print(PrintWriter out) {
        out.print(format());
    }

    public String format() {
        return String.format(TEMPLATE, FromUserName, ToUserName, CreateTime, Title, Description, MusicUrl, HQMusicUrl);
    }
}
