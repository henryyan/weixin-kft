package me.kafeitu.weixin.mp.beans;

/**
 * User: henryyan
 */
public class NewsItem {

    private String Title; // 图文消息标题
    private String Description; // 图文消息描述
    private String PicUrl; // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。
    private String Url; // 点击图文消息跳转链接

    public static final String TEMPLATE = "<Title><![CDATA[%s]]></Title>" +
            "<Description><![CDATA[%s]]></Description>" +
            "<PicUrl><![CDATA[%s]]></PicUrl>" +
            "<Url><![CDATA[%s]]></Url>";

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

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String format() {
        return String.format(TEMPLATE, Title, Description, PicUrl, Url);
    }
}
