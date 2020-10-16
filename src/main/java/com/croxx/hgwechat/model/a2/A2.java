package com.croxx.hgwechat.model.a2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class A2 {

    public static String STATUS_EDITING = "EDITING";
    public static String STATUS_OK = "OK";

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String openid;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private String fromid;
    @Column
    private String fromname;
    @Column
    private String toid;
    @Column
    private String toname;
    @Column
    private String status;

    public A2() {
        openid = "";
        content = "";
        fromid = "";
        fromname = "";
        toid = "";
        toname = "";
    }

    @Override
    public String toString() {
        return String.format("ta的学号:%s\nta的名字:%s\n表白内容:%s\n您的学号:%s\n您的名字:%s",
                toid, toname, content, fromid, fromname);
    }

    /*    Getters & Setters     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
