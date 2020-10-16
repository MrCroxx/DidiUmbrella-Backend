package com.croxx.hgwechat.service;

import com.croxx.hgwechat.model.user.User;
import com.croxx.hgwechat.model.user.UserRepository;
import com.croxx.hgwechat.req.ReqWeChatXML;
import com.croxx.hgwechat.res.ResWeChatMsg;
import com.croxx.hgwechat.res.ResWeChatTextMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class A3Service extends HGService {
    @Override
    public String ENTRY() {
        return "航概拼伞";
    }

    @Override
    public String ENTRY_TEST() {
        return "A3航概拼伞";
    }

    @Override
    public String BRIEF() {
        return "航概君正在努力添加新功能，航概拼伞请先扫描上期推送中的小程序码进入，为您带来的不便深表歉意QwQ";
    }

    @Override
    public Date START_TIME() {
        return new GregorianCalendar(2018, 4, 28, 0, 0, 0).getTime();
    }

    @Override
    public Date END_TIME() {
        return new GregorianCalendar(2020, 4, 28, 0, 0, 0).getTime();
    }

    @Override
    public String STATUS_ENTRY() {
        return STATUS.ENTRY.toString();
    }

    @Override
    public boolean hasServiceStatus(String serviceStatus) {
        return STATUS.has(serviceStatus);
    }

    @Override
    public ResWeChatMsg handle(ReqWeChatXML xml, User user, String serviceStatus) {
        return handleENTRY(xml, user);
    }

    public enum STATUS {
        ENTRY("A3_ENTRY");
        private final String text;

        private STATUS(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }

        public static boolean has(String str) {
            for (A2Service.STATUS status : A2Service.STATUS.values()) {
                if (status.toString().equals(str)) return true;
            }
            return false;
        }
    }

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UserRepository userRepository;

    private ResWeChatMsg handleENTRY(ReqWeChatXML xml, User user) {
        user.updateServiceStatus(WeChatService.SERVICE_STATUS_EXPIRE);
        userRepository.save(user);
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(),
                WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, "航概君正在努力添加新功能，航概拼伞请先扫描上期推送中的小程序码进入，为您带来的不便深表歉意QwQ");
    }
}
