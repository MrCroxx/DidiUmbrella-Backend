package com.croxx.hgwechat.model.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class RequestLog {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String openid;
    @Column
    private String servicestatus;
    @Column(columnDefinition = "TEXT")
    private String request;
    @Column(columnDefinition = "TEXT")
    private String response;
    @Column
    private Date time;

    public RequestLog() {
    }

    public RequestLog(String openid, String servicestatus, String request, String response, Date time) {
        this.openid = openid;
        this.servicestatus = servicestatus;
        this.request = request;
        this.response = response;
        this.time = time;
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

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
