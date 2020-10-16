package com.croxx.hgwechat.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Map;

@JacksonXmlRootElement(localName = "xml")
public class ReqWeChatSubscribeEvent {

    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;
    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;
    @JacksonXmlProperty(localName = "CreateTime")
    private long createTime;
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;
    @JacksonXmlProperty(localName = "Event")
    private String event;

    public ReqWeChatSubscribeEvent() {
    }

    public ReqWeChatSubscribeEvent(String toUserName, String fromUserName, long createTime, String msgType, String event) {
        this.toUserName = toUserName;
        this.fromUserName = fromUserName;
        this.createTime = createTime;
        this.msgType = msgType;
        this.event = event;
    }

    public ReqWeChatSubscribeEvent(Map<String, String> data) {
        this.toUserName = data.get("ToUserName");
        this.fromUserName = data.get("FromUserName");
        this.createTime = Long.parseLong(data.get("CreateTime"));
        this.msgType = data.get("MsgType");
        this.event = data.get("Event");
    }
}
