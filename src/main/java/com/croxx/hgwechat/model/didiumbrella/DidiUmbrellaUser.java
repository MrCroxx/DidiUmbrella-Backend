package com.croxx.hgwechat.model.didiumbrella;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DidiUmbrellaUser {

    @Id
    @JsonIgnore
    private String openid;
    @JsonIgnore
    private String session_key;
    private String token;
    private Date expire_time;

    public DidiUmbrellaUser() {
    }

    public void freshToken(String token) {
        this.token = token;
        this.expire_time = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
    }

    /*    Getters & Setters     */

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getToken() {
        return token;
    }

    public Date getExpire_time() {
        return expire_time;
    }
}
