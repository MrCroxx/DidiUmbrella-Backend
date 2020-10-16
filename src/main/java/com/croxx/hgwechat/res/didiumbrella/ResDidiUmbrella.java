package com.croxx.hgwechat.res.didiumbrella;

public class ResDidiUmbrella {

    public static int ERRCODE_SUCCESS = 20000;
    public static int ERRCODE_DATA_ERROR = 40000;
    public static int ERRCODE_SERVICE_ERROR = 50000;
    public static int ERRCODE_STATUS_DENIED = 30000;

    public static String ERRMSG_SUCCESS = "success";
    public static String ERRMSG_DATA_ERROR = "data error";
    public static String ERRMSG_SERVICE_ERROR = "service error";
    public static String ERRMSG_STATUS_DENIED = "status denied";

    protected int errcode;
    protected String errmsg;

    public ResDidiUmbrella() {
    }

    public ResDidiUmbrella(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
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
}
