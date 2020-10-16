package com.croxx.hgwechat.model.a1;

import javax.persistence.*;
import java.util.Date;

@Entity
public class A1Open {

    public static String STATUS_NOTIFIED = "NOTIFIED";
    public static String STATUS_UNNOTIFIED = "UNNOTIFIED";


    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long maker_aid;
    @Column
    private Long opener_aid;
    @Column
    private String status;
    @Column
    private Date opentime;

    public A1Open() {
    }

    /*    Getters & Setters     */


    public Long getId() {
        return id;
    }

    public Long getMaker_aid() {
        return maker_aid;
    }

    public void setMaker_aid(Long maker_aid) {
        this.maker_aid = maker_aid;
    }

    public Long getOpener_aid() {
        return opener_aid;
    }

    public void setOpener_aid(Long opener_aid) {
        this.opener_aid = opener_aid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOpentime() {
        return opentime;
    }

    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }
}
