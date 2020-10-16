package com.croxx.hgwechat.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class User {
    @Id
    @Column(length = 128)
    private String openID;
    @Column
    private String serviceStatus;
    @Column
    private Date serviceUpdateTime;

    public User() {
    }

    public User(@NotNull String openID) {
        this.openID = openID;
    }

    public void updateServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        this.serviceUpdateTime = new Date();
    }

    /*    Getters & Setters     */

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public Date getServiceUpdateTime() {
        return serviceUpdateTime;
    }


}
