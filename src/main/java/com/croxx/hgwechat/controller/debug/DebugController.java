package com.croxx.hgwechat.controller.debug;

import com.croxx.hgwechat.controller.v1.WeChatServiceControllerV1;
import com.croxx.hgwechat.req.ReqWeChatTextMsg;
import com.croxx.hgwechat.service.WeChatAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private WeChatAuthService wechatAuthService;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String auth() {
        return wechatAuthService.getAccessToken();
    }

    @RequestMapping(value = "/msg",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE})
    public ReqWeChatTextMsg reply(@RequestBody ReqWeChatTextMsg reqWeChatTextMsg) {

        Logger logger = LoggerFactory.getLogger(WeChatServiceControllerV1.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info(mapper.writeValueAsString(reqWeChatTextMsg));
            return reqWeChatTextMsg;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return reqWeChatTextMsg;
    }
}
