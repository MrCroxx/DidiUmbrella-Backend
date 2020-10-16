package com.croxx.hgwechat.req;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class ReqWeChatTokenCheck {

    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
    private String token;

    public ReqWeChatTokenCheck() {
    }

    public ReqWeChatTokenCheck(@NotNull String signature, @NotNull String timestamp, @NotNull String nonce, @NotNull String echostr, @NotNull String token) {
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.echostr = echostr;
        this.token = token;
    }

    public boolean check() {
        if (signature != null && timestamp != null && nonce != null && echostr != null) {
            String strs[] = {token, timestamp, nonce};
            Arrays.sort(strs);
            String text = strs[0] + strs[1] + strs[2];
            String sha1 = DigestUtils.sha1Hex(text);
            if (sha1.equals(signature)) return true;
        }
        return false;
    }

    /*    Getters & Setters     */

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*
signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
timestamp	时间戳
nonce	随机数
echostr	随机字符串
 */

}
