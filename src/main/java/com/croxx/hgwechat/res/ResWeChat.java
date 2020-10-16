package com.croxx.hgwechat.res;

public class ResWeChat {
    private int errcode;
    private String errmsg;

    public ResWeChat(){}

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
}
