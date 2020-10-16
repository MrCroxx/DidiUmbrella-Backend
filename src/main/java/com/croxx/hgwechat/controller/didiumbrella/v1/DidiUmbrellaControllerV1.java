package com.croxx.hgwechat.controller.didiumbrella.v1;

import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaOrder;
import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaUser;
import com.croxx.hgwechat.req.didiumbrella.ReqDidiUmbrellaOrder;
import com.croxx.hgwechat.res.didiumbrella.*;
import com.croxx.hgwechat.service.didiumbrella.DidiUmbrellaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/didiumbrella/v1")
public class DidiUmbrellaControllerV1 {

    @Autowired
    private DidiUmbrellaService didiUmbrellaService;

    @PostMapping("/login/{js_code}")
    public ResDidiUmbrellaWithUser login(@PathVariable("js_code") String js_code) {
        DidiUmbrellaUser user = didiUmbrellaService.login(js_code);
        return new ResDidiUmbrellaWithUser(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, user);
    }

    @PostMapping("/order/new")
    public ResDidiUmbrella order(@RequestBody ReqDidiUmbrellaOrder request) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(request.getToken());
        if (user != null) {
            DidiUmbrellaOrder order = didiUmbrellaService.order(request, user.getOpenid());
            return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

    @GetMapping("/order/latest/{token}")
    public ResDidiUmbrella hasOrder(@PathVariable("token") String token) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(token);
        if (user != null) {
            DidiUmbrellaOrder order = didiUmbrellaService.getLatestFromOrderByOpenid(user.getOpenid());
            if (order == null) {
                order = didiUmbrellaService.getLatestToOrderByOpenid(user.getOpenid());
            }
            return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

    @DeleteMapping("/order/{oid}/cancel/{token}")
    public ResDidiUmbrella cancel(@PathVariable("oid") Long oid, @PathVariable("token") String token) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(token);
        if (user != null) {
            DidiUmbrellaOrder order = didiUmbrellaService.cancelOrderByOidAndOpenid(oid, user.getOpenid());
            if (order == null) {
                return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_DATA_ERROR, ResDidiUmbrella.ERRMSG_DATA_ERROR);
            } else {
                return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
            }
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

    @PostMapping("/order/{oid}/finish/{token}")
    public ResDidiUmbrella finish(@PathVariable("oid") Long oid, @PathVariable("token") String token) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(token);
        if (user != null) {
            DidiUmbrellaOrder order = didiUmbrellaService.finishOrderByOidAndOpenid(oid, user.getOpenid());
            if (order == null) {
                return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_DATA_ERROR, ResDidiUmbrella.ERRMSG_DATA_ERROR);
            } else {
                return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
            }
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

    @GetMapping("/order/waiting")
    public ResDidiUmbrella getWaitingOrders() {
        List<DidiUmbrellaOrder> orders = didiUmbrellaService.getWaitingOrders();
        return new ResDidiUmbrellaWithOrders(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, orders);
    }

    @GetMapping("/order/history/{token}")
    public ResDidiUmbrella getHistoryOrders(@PathVariable("token") String token) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(token);
        if (user != null) {
            List<DidiUmbrellaOrder> orders = didiUmbrellaService.getHistoryOrdersByOpenid(user.getOpenid());
            return new ResDidiUmbrellaWithOrders(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, orders);
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

    @PostMapping("/order/{oid}/take/{token}/{nickname}")
    public ResDidiUmbrella takeOrder(@PathVariable("oid") long oid, @PathVariable("token") String token,
                                     @PathVariable("nickname") String nickname) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(token);
        if (user != null) {
            DidiUmbrellaOrder order = didiUmbrellaService.takeOrder(oid, user.getOpenid(), nickname);
            if (order == null) {
                return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_DATA_ERROR, ResDidiUmbrella.ERRMSG_DATA_ERROR);
            } else {
                return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
            }
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

    @PostMapping("/order/{oid}/fresh/{token}")
    public ResDidiUmbrella freshOrder(@PathVariable("oid") long oid, @PathVariable("token") String token) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(token);
        DidiUmbrellaOrder order = didiUmbrellaService.freshOrderByOidAndOpenid(oid, user.getOpenid());
        return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
    }

}
