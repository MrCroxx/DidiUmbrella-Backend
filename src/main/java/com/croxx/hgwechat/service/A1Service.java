package com.croxx.hgwechat.service;

import com.croxx.hgwechat.model.a1.A1;
import com.croxx.hgwechat.model.a1.A1Repository;
import com.croxx.hgwechat.model.user.User;
import com.croxx.hgwechat.model.user.UserRepository;
import com.croxx.hgwechat.req.ReqWeChatXML;
import com.croxx.hgwechat.res.ResWeChatMsg;
import com.croxx.hgwechat.res.ResWeChatTextMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class A1Service extends HGService {

    @Override
    public String ENTRY() {
        return "纸飞机";
    }

    @Override
    public String ENTRY_TEST() {
        return "A1纸飞机";
    }

    @Override
    public String BRIEF() {
        return "简介";
    }

    @Override
    public Date START_TIME() {
        return new GregorianCalendar(2018, 6, 1).getTime();
    }

    @Override
    public Date END_TIME() {
        return new GregorianCalendar(2020, 6, 1).getTime();
    }

    @Override
    public String STATUS_ENTRY() {
        return STATUS.ENTRY.toString();
    }

    @Override
    public boolean hasServiceStatus(String serviceStatus) {
        return STATUS.has(serviceStatus);
    }

    public enum STATUS {
        ENTRY("A1_ENTRY"),
        CHOOSE_SERVICE("A1_CHOOSE_SERVICE"),
        CONFIRM_REWRITE("A1_CONFIRM_REWRITE"),
        WRITE_CONTENT("A1_WRITE_CONTENT"),
        WRITE_NAME("A1_WRITE_NAME"),
        WIRTE_WECHAT("A1_WRITE_WECHAT"),
        CONFIRM("A1_CONFIRM"),
        CHOOSE_CMD("A1_CHOOSE_CMD");

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
    private A1Repository a1Repository;
    @Autowired
    private UserRepository userRepository;

    @Value("${reply.a1.services}")
    private String reply_services;
    @Value("${reply.a1.opened}")
    private String reply_opened;
    @Value("${reply.a1.confirm_rewrite}")
    private String reply_confirm_rewrite;
    @Value("${reply.a1.write_content}")
    private String reply_write_content;
    @Value("${reply.a1.write_name}")
    private String reply_write_name;
    @Value("${reply.a1.write_wechat}")
    private String reply_write_wechat;
    @Value("${reply.a1.require_a1}")
    private String reply_require_a1;

    @Override
    public ResWeChatMsg handle(ReqWeChatXML xml, User user, String serviceStatus) {
        return null;
    }

    private ResWeChatMsg handle_ENTRY(ReqWeChatXML xml, User user, String serviceStatus) {
        String content = "";
        List<A1> openers = getOpeners(user);
        if (openers.size() > 0) {
            String openersInfo = "";
            for (A1 opener : openers) {
                openersInfo += String.format("%s 微信号:%s\n", opener.getName(), opener.getWechat());
            }
            content += String.format(reply_opened, openersInfo) + "\n";
        }
        content += reply_services;
        user.updateServiceStatus(STATUS.CHOOSE_SERVICE.toString());
        userRepository.save(user);
        return new ResWeChatTextMsg(xml.getFromUserName(), xml.getToUserName()
                , WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, content);
    }

    private ResWeChatMsg handle_CHOOSE_SERVICE(ReqWeChatXML xml, User user, String serviceStatus) {
        String cmd = xml.getContent();
        A1 a1 = a1Repository.findA1ByOpenidAndStatus(user.getOpenID(), A1.STATUS_OK);
        if (cmd.equals("制作")) {
            if (a1 == null) {
                //制作
                user.updateServiceStatus(STATUS.WRITE_CONTENT.toString());
                userRepository.save(user);
                return new ResWeChatTextMsg(xml.getFromUserName(), xml.getToUserName()
                        , WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_write_content);
            } else {
                //重做
                user.updateServiceStatus(STATUS.CONFIRM_REWRITE.toString());
                userRepository.save(user);
                return new ResWeChatTextMsg(xml.getFromUserName(), xml.getToUserName()
                        , WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_confirm_rewrite);
            }
        } else if (cmd.equals("接收")) {
            if (a1 == null) {
                //请先制作
                user.updateServiceStatus(STATUS.WRITE_CONTENT.toString());
                userRepository.save(user);
                return new ResWeChatTextMsg(xml.getFromUserName(), xml.getToUserName()
                        , WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_require_a1 + "\n" + reply_write_content);
            } else {
                //TODO: 接收
                return new ResWeChatTextMsg(xml.getFromUserName(), xml.getToUserName()
                        , WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_services);
            }
        } else {
            return new ResWeChatTextMsg(xml.getFromUserName(), xml.getToUserName()
                    , WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_services);
        }
    }

    private List<A1> getOpeners(User user) {
        A1 a1 = a1Repository.findA1ByOpenidAndStatus(user.getOpenID(), A1.STATUS_OK);
        List<A1> openers = new ArrayList<>();
        if (a1 == null) return openers;
        openers = a1Repository.findOpeners(a1.getId());
        return openers;
    }


}
