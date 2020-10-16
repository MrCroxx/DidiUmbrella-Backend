package com.croxx.hgwechat.controller.v1;

import com.croxx.hgwechat.req.ReqWeChatTokenCheck;
import com.croxx.hgwechat.req.ReqWeChatXML;
import com.croxx.hgwechat.res.ResWeChatTextMsg;
import com.croxx.hgwechat.service.WeChatAuthService;
import com.croxx.hgwechat.service.WeChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class WeChatServiceControllerV1 {

    @Autowired
    private WeChatAuthService wechatAuthService;
    @Autowired
    private WeChatService wechatService;

    @GetMapping(value = "/wechat")
    public String authToken(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                            @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        ReqWeChatTokenCheck req = new ReqWeChatTokenCheck(signature, timestamp, nonce, echostr, wechatAuthService.getToken());
        if (req.check()) {
            return req.getEchostr();
        }
        return "false";
    }

    @PostMapping(value = "/wechat", consumes = {MediaType.TEXT_XML_VALUE}, produces = {MediaType.TEXT_XML_VALUE})
    public Object wechatServiceAPI(@RequestBody ReqWeChatXML xml) {

        if (xml.getMsgType().equals(WeChatService.TYPE_TEXT)) {
            return wechatService.handleText(xml);
        } else if (xml.getMsgType().equals(WeChatService.TYPE_EVENT)) {
            return wechatService.handleEvent(xml);
        }
        return "success";
    }
}
