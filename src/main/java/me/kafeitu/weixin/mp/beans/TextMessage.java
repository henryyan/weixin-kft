package me.kafeitu.weixin.mp.beans;

import java.io.PrintWriter;

/**
 * User: henryyan
 */
public class TextMessage extends Message {

    public static final String TEMPLATE = "<xml>" +
            "<ToUserName><![CDATA[%s]]></ToUserName>" +
            "<FromUserName><![CDATA[%s]]></FromUserName>" +
            "<CreateTime>%s</CreateTime>" +
            "<MsgType><![CDATA[text]]></MsgType>" +
            "<Content><![CDATA[%s]]></Content>" +
            "<FuncFlag>0</FuncFlag>" +
            "</xml>";

    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void print(PrintWriter out) {
        out.print(format());
    }

    public String format() {
        return String.format(TEMPLATE, FromUserName, ToUserName, CreateTime, Content);
    }
}