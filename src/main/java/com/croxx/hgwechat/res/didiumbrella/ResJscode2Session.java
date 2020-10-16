package com.croxx.hgwechat.res.didiumbrella;

public class ResJscode2Session {

    private int errcode;
    private String errmsg;
    private String session_key;
    private String openid;

    public ResJscode2Session() {
    }

    /*    Getters & Setters     */

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
