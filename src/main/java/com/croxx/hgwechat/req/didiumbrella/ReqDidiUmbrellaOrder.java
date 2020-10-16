package com.croxx.hgwechat.req.didiumbrella;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ReqDidiUmbrellaOrder {

    private String token;
    private String from_nickname;
    private double from_longitude;
    private double from_latitude;
    private double to_longitude;
    private double to_latitude;
    private String from_position;
    private String to_position;
    private String from_contact;
    private String from_wifi;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date event_time;

    public ReqDidiUmbrellaOrder() {
    }

    /*    Getters & Setters     */

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFrom_nickname() {
        return from_nickname;
    }

    public void setFrom_nickname(String from_nickname) {
        this.from_nickname = from_nickname;
    }

    public double getFrom_longitude() {
        return from_longitude;
    }

    public void setFrom_longitude(double from_longitude) {
        this.from_longitude = from_longitude;
    }

    public double getFrom_latitude() {
        return from_latitude;
    }

    public void setFrom_latitude(double from_latitude) {
        this.from_latitude = from_latitude;
    }

    public double getTo_longitude() {
        return to_longitude;
    }

    public void setTo_longitude(double to_longitude) {
        this.to_longitude = to_longitude;
    }

    public double getTo_latitude() {
        return to_latitude;
    }

    public void setTo_latitude(double to_latitude) {
        this.to_latitude = to_latitude;
    }

    public String getFrom_position() {
        return from_position;
    }

    public void setFrom_position(String from_position) {
        this.from_position = from_position;
    }

    public String getTo_position() {
        return to_position;
    }

    public void setTo_position(String to_position) {
        this.to_position = to_position;
    }

    public String getFrom_contact() {
        return from_contact;
    }

    public void setFrom_contact(String from_contact) {
        this.from_contact = from_contact;
    }

    public String getFrom_wifi() {
        return from_wifi;
    }

    public void setFrom_wifi(String from_wifi) {
        this.from_wifi = from_wifi;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }
}
