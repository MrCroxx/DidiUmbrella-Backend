package com.croxx.hgwechat.model.didiumbrella;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DidiUmbrellaOrder {

    public static String STATUS_WAITING = "WAITING";
    public static String STATUS_RUNNING = "RUNNING";
    public static String STATUS_FINISH = "FINISH";
    public static String STATUS_CANCEL = "CANCEL";

    @Id
    @GeneratedValue
    private Long id;
    @Column
    @JsonIgnore
    private String from_openid;
    @Column
    @JsonIgnore
    private String to_openid;
    @Column
    private String from_nickname;
    @Column
    private String to_nickname;
    @Column
    private double from_longitude;
    @Column
    private double from_latitude;
    @Column
    private double to_longitude;
    @Column
    private double to_latitude;
    @Column
    private String from_position;
    @Column
    private String to_position;
    @Column
    private String from_contact;
    @Column
    private String to_contact;
    @Column
    private String from_wifi;
    @Column
    private String to_wifi;
    @Column
    private String remark;
    @Column
    private String comment;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date solve_time;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date event_time;
    @Column
    private String status;

    public DidiUmbrellaOrder() {
    }

    public DidiUmbrellaOrder(String from_openid, String from_nickname,
                             double from_longitude, double from_latitude, double to_longitude, double to_latitude,
                             String from_position, String to_position, String from_contact,String from_wifi,
                             String remark, Date create_time, Date event_time, String status) {
        this.from_openid = from_openid;
        this.from_nickname = from_nickname;
        this.from_longitude = from_longitude;
        this.from_latitude = from_latitude;
        this.to_longitude = to_longitude;
        this.to_latitude = to_latitude;
        this.from_position = from_position;
        this.from_contact = from_contact;
        this.to_position = to_position;
        this.from_wifi = from_wifi;
        this.remark = remark;
        this.create_time = create_time;
        this.event_time = event_time;
        this.status = status;
    }



    /*    Getteres & Setters     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom_openid() {
        return from_openid;
    }

    public void setFrom_openid(String from_openid) {
        this.from_openid = from_openid;
    }

    public String getTo_openid() {
        return to_openid;
    }

    public void setTo_openid(String to_openid) {
        this.to_openid = to_openid;
    }

    public String getFrom_nickname() {
        return from_nickname;
    }

    public void setFrom_nickname(String from_nickname) {
        this.from_nickname = from_nickname;
    }

    public String getTo_nickname() {
        return to_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname = to_nickname;
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

    public String getTo_contact() {
        return to_contact;
    }

    public void setTo_contact(String to_contact) {
        this.to_contact = to_contact;
    }

    public String getFrom_wifi() {
        return from_wifi;
    }

    public void setFrom_wifi(String from_wifi) {
        this.from_wifi = from_wifi;
    }

    public String getTo_wifi() {
        return to_wifi;
    }

    public void setTo_wifi(String to_wifi) {
        this.to_wifi = to_wifi;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getSolve_time() {
        return solve_time;
    }

    public void setSolve_time(Date solve_time) {
        this.solve_time = solve_time;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
