package com.croxx.hgwechat.service.didiumbrella;

import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaOrder;
import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaOrderReposity;
import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaUser;
import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaUserReposity;
import com.croxx.hgwechat.req.didiumbrella.ReqDidiUmbrellaOrder;
import com.croxx.hgwechat.req.didiumbrella.ReqDidiUmbrellaTake;
import com.croxx.hgwechat.res.didiumbrella.ResJscode2Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DidiUmbrellaService {

    @Value("${wechat.didiumbrella.appid}")
    private String appid;
    @Value("${wechat.didiumbrella.secret}")
    private String secret;
    @Value("${wechat.didiumbrella.grant_type}")
    private String grant_type;
    @Value("${wechat.didiumbrella.url}")
    private String url;

    @Autowired
    private DidiUmbrellaOrderReposity didiUmbrellaOrderReposity;
    @Autowired
    private DidiUmbrellaUserReposity didiUmbrellaUserReposity;

    /*
    public ResJscode2Session jscode2session(@NotNull String jscode) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("grant_type", grant_type);
        params.put("js_code", jscode);
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        try {
            return mapper.readValue(response.getBody(), ResJscode2Session.class);
        } catch (IOException e) {
            return null;
        }
    }
    */

    private static String getRandomString(int length) {
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    private String getToken(DidiUmbrellaUser user) {
        String text = user.getOpenid() + System.currentTimeMillis() + getRandomString(64);
        return DigestUtils.md5Hex(text);
    }

    public DidiUmbrellaUser login(@NotNull String js_code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("grant_type", grant_type);
        params.put("js_code", js_code);
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        try {
            ResJscode2Session session = mapper.readValue(response.getBody(), ResJscode2Session.class);
            DidiUmbrellaUser user = didiUmbrellaUserReposity.findByOpenid(session.getOpenid());
            if (user == null) {
                user = new DidiUmbrellaUser();
                user.setOpenid(session.getOpenid());
                user.setSession_key(session.getSession_key());
                user.freshToken(getToken(user));
            } else {
                user.freshToken(getToken(user));
            }
            didiUmbrellaUserReposity.save(user);
            return user;
        } catch (IOException e) {
            return null;
        }
    }

    public DidiUmbrellaUser getUserByToken(@NotNull String token) {
        return didiUmbrellaUserReposity.findByToken(token);
    }

    public DidiUmbrellaOrder order(ReqDidiUmbrellaOrder request, String openid) {
        DidiUmbrellaOrder order = new DidiUmbrellaOrder(
                openid, request.getFrom_nickname(),
                request.getFrom_longitude(), request.getFrom_latitude(), request.getTo_longitude(), request.getTo_latitude(),
                request.getFrom_position(), request.getTo_position(),
                request.getFrom_contact(), request.getFrom_wifi(), request.getRemark(),
                new Date(), request.getEvent_time(), DidiUmbrellaOrder.STATUS_WAITING
        );
        didiUmbrellaOrderReposity.save(order);
        return order;
    }

    public DidiUmbrellaOrder getLatestFromOrderByOpenid(String openid) {
        List<DidiUmbrellaOrder> orders = didiUmbrellaOrderReposity.findLatestFromOrder(openid);
        if (orders.isEmpty()) return null;
        DidiUmbrellaOrder order = orders.get(0);
        if (order.getStatus().equals(DidiUmbrellaOrder.STATUS_RUNNING) || order.getStatus().equals(DidiUmbrellaOrder.STATUS_WAITING))
            return order;
        return null;
    }

    public DidiUmbrellaOrder getLatestToOrderByOpenid(String openid) {
        List<DidiUmbrellaOrder> orders = didiUmbrellaOrderReposity.findLatestToOrder(openid);
        if (orders.isEmpty()) return null;
        DidiUmbrellaOrder order = orders.get(0);
        if (order.getStatus().equals(DidiUmbrellaOrder.STATUS_RUNNING) || order.getStatus().equals(DidiUmbrellaOrder.STATUS_WAITING))
            return order;
        return null;
    }

    public DidiUmbrellaOrder getOrderByOidAndOpenid(long oid, String openid) {
        DidiUmbrellaOrder order = didiUmbrellaOrderReposity.findById(oid);
        if (order == null) return order;
        if (!order.getFrom_openid().equals(openid)) return null;
        return order;
    }

    public DidiUmbrellaOrder cancelOrderByOidAndOpenid(long oid, String openid) {
        DidiUmbrellaOrder order = didiUmbrellaOrderReposity.findById(oid);
        if (order == null) return order;
        if (!order.getFrom_openid().equals(openid)) return null;
        order.setStatus(DidiUmbrellaOrder.STATUS_CANCEL);
        didiUmbrellaOrderReposity.save(order);
        return order;
    }

    public List<DidiUmbrellaOrder> getWaitingOrders() {
        return didiUmbrellaOrderReposity.findByStatus(DidiUmbrellaOrder.STATUS_WAITING);
    }

    public List<DidiUmbrellaOrder> getHistoryOrdersByOpenid(String openid) {
        return didiUmbrellaOrderReposity.findHistory(openid);
    }

    public DidiUmbrellaOrder takeOrder(String openid, ReqDidiUmbrellaTake request) {
        DidiUmbrellaOrder order = didiUmbrellaOrderReposity.findById(request.getOid());
        if (order == null) return order;
        if (!order.getStatus().equals(DidiUmbrellaOrder.STATUS_WAITING)) return null;
        order.setStatus(DidiUmbrellaOrder.STATUS_RUNNING);
        order.setSolve_time(new Date());
        order.setTo_openid(openid);
        order.setTo_nickname(request.getTo_nickname());
        order.setTo_contact(request.getTo_contact());
        order.setTo_wifi(request.getTo_wifi());
        didiUmbrellaOrderReposity.save(order);
        return order;
    }

    public DidiUmbrellaOrder takeOrder(long oid, String openid, String nickname) {
        DidiUmbrellaOrder order = didiUmbrellaOrderReposity.findById(oid);
        if (order == null) return order;
        if (!order.getStatus().equals(DidiUmbrellaOrder.STATUS_WAITING)) return null;
        order.setStatus(DidiUmbrellaOrder.STATUS_RUNNING);
        order.setSolve_time(new Date());
        order.setTo_openid(openid);
        order.setTo_nickname(nickname);
        didiUmbrellaOrderReposity.save(order);
        return order;
    }

    public DidiUmbrellaOrder finishOrderByOidAndOpenid(long oid, String openid) {
        DidiUmbrellaOrder order = didiUmbrellaOrderReposity.findById(oid);
        if (order == null) return order;
        if (!order.getFrom_openid().equals(openid)) return null;
        order.setStatus(DidiUmbrellaOrder.STATUS_FINISH);
        didiUmbrellaOrderReposity.save(order);
        return order;
    }

    public DidiUmbrellaOrder freshOrderByOidAndOpenid(long oid, String openid) {
        DidiUmbrellaOrder order = didiUmbrellaOrderReposity.findById(oid);
        if (order == null) return order;
        if (!order.getFrom_openid().equals(openid) && !order.getTo_openid().equals(openid)) return null;
        return order;
    }

}
