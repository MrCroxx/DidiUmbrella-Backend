package com.croxx.hgwechat.service;

import com.croxx.hgwechat.res.ResWeChatAuth;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatAuthService {

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${wechat.grant_type.auth}")
    private String grant_type;
    @Value("${wechat.api.auth}")
    private String URL_AUTH;
    @Value("${wechat.wechatid}")
    private String wechatID;
    @Value("${wechat.token}")
    private String token;

    private String access_token = null;
    private Date expireDate = null;

    private String auth() {
        Logger logger = LoggerFactory.getLogger(WeChatAuthService.class);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("appid", appid);
        uriVariables.put("secret", secret);
        uriVariables.put("grant_type", grant_type);
        ResponseEntity<ResWeChatAuth> res = restTemplate.getForEntity(URL_AUTH, ResWeChatAuth.class, uriVariables);


        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info(mapper.writeValueAsString(res.getBody()));
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        ResWeChatAuth resWeChatAuth = res.getBody();
        access_token = resWeChatAuth.getAccess_token();
        expireDate = new Date(System.currentTimeMillis() + resWeChatAuth.getExpires_in() * 1000);
        return access_token;
    }



    public String getAccessToken() {
        if (expireDate == null) return auth();
        else if (new Date().after(expireDate)) return auth();
        else return access_token;
    }

    /*    Getters & Setters     */

    public String getWechatID() {
        return wechatID;
    }

    public String getToken() {
        return token;
    }
}
