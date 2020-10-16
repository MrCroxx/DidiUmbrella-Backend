package com.croxx.hgwechat.controller.didiumbrella.v2;

import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaOrder;
import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaUser;
import com.croxx.hgwechat.req.didiumbrella.ReqDidiUmbrellaTake;
import com.croxx.hgwechat.res.didiumbrella.ResDidiUmbrella;
import com.croxx.hgwechat.res.didiumbrella.ResDidiUmbrellaWithOrder;
import com.croxx.hgwechat.service.didiumbrella.DidiUmbrellaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/didiumbrella/v2")
public class DidiUmbrellaControllerV2 {

    @Autowired
    private DidiUmbrellaService didiUmbrellaService;


    @PostMapping("/order/take")
    public ResDidiUmbrella takeOrder(@RequestBody ReqDidiUmbrellaTake request) {
        DidiUmbrellaUser user = didiUmbrellaService.getUserByToken(request.getToken());
        if (user != null) {
            DidiUmbrellaOrder order = didiUmbrellaService.takeOrder(user.getOpenid(), request);
            if (order == null) {
                return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_DATA_ERROR, ResDidiUmbrella.ERRMSG_DATA_ERROR);
            } else {
                return new ResDidiUmbrellaWithOrder(ResDidiUmbrella.ERRCODE_SUCCESS, ResDidiUmbrella.ERRMSG_SUCCESS, order);
            }
        } else {
            return new ResDidiUmbrella(ResDidiUmbrella.ERRCODE_SERVICE_ERROR, ResDidiUmbrella.ERRMSG_SERVICE_ERROR);
        }
    }

}
