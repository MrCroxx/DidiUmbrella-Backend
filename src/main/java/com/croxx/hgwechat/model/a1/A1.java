package com.croxx.hgwechat.model.a1;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class A1 {

    public static String STATUS_OK = "OK";
    public static String STATUS_EDITING = "EDITING";
    public static String STATUS_EXPIRED = "EXPIRED";

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String openid;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private String name;
    @Column
    private String wechat;
    @Column
    private Date createdate;
    @Column
    private String status;

    public A1() {
    }

    /*    Getters & Setters     */

    public long getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
