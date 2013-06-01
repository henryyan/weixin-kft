package me.kafeitu.weixin.mp.beans;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片消息
 * User: henryyan
 */
public class ImageMessage extends Message {

    public static final String TEMPLATE = "<xml>" +
            "<ToUserName><![CDATA[%s]]></ToUserName>" +
            "<FromUserName><![CDATA[%s]]></FromUserName>" +
            "<CreateTime>%s</CreateTime>" +
            "<MsgType><![CDATA[news]]></MsgType>" +
            "<ArticleCount>%s</ArticleCount>" +
            "<Articles>%s</Articles>" +
            "<FuncFlag>1</FuncFlag>" +
            "</xml>";

    private int ArticleCount; // 图文消息个数，限制为10条以内
    private List<NewsItem> items = new ArrayList<NewsItem>();

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }

    public void print(PrintWriter out) {
        out.print(format());
    }

    public String format() {
        return String.format(TEMPLATE, FromUserName, ToUserName, CreateTime, getItems().size(), getItemXml());
    }

    private String getItemXml() {
        StringBuilder sb = new StringBuilder();
        for (NewsItem item : items) {
            sb.append("<item>" + item.format() + "</item>");
        }
        return sb.toString();
    }
}
