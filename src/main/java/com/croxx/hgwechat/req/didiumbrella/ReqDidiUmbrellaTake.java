package com.croxx.hgwechat.req.didiumbrella;

public class ReqDidiUmbrellaTake {

    private long oid;
    private String token;
    private String to_nickname;
    private String to_contact;
    private String to_wifi;

    public ReqDidiUmbrellaTake() {
    }

    /*    Getters & Setters     */

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTo_nickname() {
        return to_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname = to_nickname;
    }

    public String getTo_contact() {
        return to_contact;
    }

    public void setTo_contact(String to_contact) {
        this.to_contact = to_contact;
    }

    public String getTo_wifi() {
        return to_wifi;
    }

    public void setTo_wifi(String to_wifi) {
        this.to_wifi = to_wifi;
    }
}
