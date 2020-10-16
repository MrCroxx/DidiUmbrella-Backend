package com.croxx.hgwechat.service;

import com.croxx.hgwechat.config.NormalReplyFactory;
import com.croxx.hgwechat.model.log.RequestLog;
import com.croxx.hgwechat.model.log.RequestLogRepository;
import com.croxx.hgwechat.model.user.User;
import com.croxx.hgwechat.model.user.UserRepository;
import com.croxx.hgwechat.req.ReqWeChatXML;
import com.croxx.hgwechat.res.ResWeChatMsg;
import com.croxx.hgwechat.res.ResWeChatTextMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class WeChatService {

    public static String TYPE_NAME = "MsgType";
    public static String TYPE_TEXT = "text";
    public static String TYPE_EVENT = "event";

    public static String EVENT_SUBSCRIBE = "subscribe";

    public static String SERVICE_STATUS_EXPIRE = "SERVICE_STATUS_EXPIRE";

    @Value("${reply.subscribe}")
    private String replySubscribe;
    @Value("${reply.timeout}")
    private int timeoutSecond;
    @Value("${reply.error}")
    private String reply_error;
    @Value("${reply.early}")
    private String reply_early;
    @Value("${reply.late}")
    private String reply_late;

    private String ASK_ACT = "活动";
    private String ASK_ALLACT = "所有活动";

    @Value("${reply.act}")
    private String reply_act;
    @Value("${reply.noact}")
    private String reply_noact;
    @Value("${reply.allact}")
    private String reply_allact;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestLogRepository requestLogRepository;
    @Autowired
    private A2Service a2Service;
    @Autowired
    private A3Service a3Service;
    @Autowired
    private NormalReplyFactory normalReplyFactory;

    public static long getCreateTime() {
        return new Date().getTime() / 1000;
    }

    private Logger logger = LoggerFactory.getLogger(WeChatService.class);

    class EntryResult {
        private boolean ok;
        private ResWeChatMsg res;

        public EntryResult(boolean ok, ResWeChatMsg res) {
            this.ok = ok;
            this.res = res;
        }

        public boolean isOk() {
            return ok;
        }

        public ResWeChatMsg getRes() {
            return res;
        }
    }

    // TODO : 在这里注册服务
    private List<HGService> getServices() {
        return Arrays.asList(
                a2Service, a3Service
        );
    }

    public ResWeChatMsg handleText(ReqWeChatXML xml) {

        User user = getUserByOpenID(xml.getFromUserName());
        ResWeChatMsg res = null;
        Date now = new Date();
        // Handle Entry Message
        EntryResult er = new EntryResult(false, null);
        for (HGService service : getServices()) {
            if (er.isOk()) break;
            er = caseService(xml, user, now, service);
            res = er.getRes();
        }

        if (!er.isOk()) {
            if (xml.getContent().equals(ASK_ACT)) {
                res = new ResWeChatTextMsg(
                        xml.getFromUserName(), xml.getToUserName(), getCreateTime(), TYPE_TEXT, createActMsg(false)
                );
            } else if (xml.getContent().equals(ASK_ALLACT)) {
                res = new ResWeChatTextMsg(
                        xml.getFromUserName(), xml.getToUserName(), getCreateTime(), TYPE_TEXT, createActMsg(true)
                );
            }
        }

        // Handle Service Message
        String serviceStatus = getServiceStatusByUser(user);

        if (!serviceStatus.equals(SERVICE_STATUS_EXPIRE)) {
            for (HGService service : getServices()) {
                if (service.hasServiceStatus(serviceStatus)) {
                    res = service.handle(xml, user, serviceStatus);
                    break;
                }
            }
        }

        if (res == null) res = otherMsgHandler(xml);

        // Note Logs
        noteServiceLog(xml, user.getServiceStatus(), res);

        return res;
    }

    public Object handleEvent(ReqWeChatXML xml) {
        if (xml.getEvent().equals(EVENT_SUBSCRIBE)) {
            return new ResWeChatTextMsg(
                    xml.getFromUserName(), xml.getToUserName(), getCreateTime(), TYPE_TEXT, replySubscribe
            );
        }
        return null;
    }

    public ResWeChatMsg handleERROR(ReqWeChatXML xml, User user) {
        user.updateServiceStatus(WeChatService.SERVICE_STATUS_EXPIRE);
        userRepository.save(user);
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(),
                WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_error);
    }

    private void enterService(User user, String serviceStatus) {
        user.updateServiceStatus(serviceStatus);
        userRepository.save(user);
    }

    private String getServiceStatusByUser(User user) {
        if (user.getServiceUpdateTime() == null)
            return SERVICE_STATUS_EXPIRE;
        if (new Date(System.currentTimeMillis() - timeoutSecond * 1000).after(user.getServiceUpdateTime()))
            return SERVICE_STATUS_EXPIRE;
        return user.getServiceStatus();
    }

    private User getUserByOpenID(String openID) {
        User user = userRepository.findByOpenID(openID);
        if (user == null)
            user = new User(openID);
        userRepository.save(user);
        return user;
    }

    private ResWeChatMsg otherMsgHandler(ReqWeChatXML xml) {
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(), getCreateTime(), TYPE_TEXT,
                "回复【活动】可以查看现在正在进行的活动哦" + normalReplyFactory.createRandomReply()
        );
    }

    private ResWeChatTextMsg createEarlyMsg(ReqWeChatXML xml) {
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(), getCreateTime(), TYPE_TEXT, reply_early
        );
    }

    private ResWeChatTextMsg createLateMsg(ReqWeChatXML xml) {
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(), getCreateTime(), TYPE_TEXT, reply_late
        );
    }

    private void noteServiceLog(ReqWeChatXML xml, String serviceStatus, Object res) {
        ObjectMapper mapper = new ObjectMapper();
        String request = null;
        String response = null;
        try {
            request = mapper.writeValueAsString(xml);
            response = mapper.writeValueAsString(res);
            logger.info("WeChatRequest:{} ServiceStatus:{} Response:{}", request, serviceStatus, response);
            RequestLog log = new RequestLog(xml.getFromUserName(), serviceStatus, request, response, new Date());
            requestLogRepository.save(log);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private String createActMsg(boolean all) {
        if (all) {
            String briefs = "";
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            for (HGService service : getServices()) {
                briefs += String.format("【%s】\n", service.ENTRY());
                briefs += String.format("%s\n", service.BRIEF());
                briefs += String.format("%s - %s\n", df.format(service.START_TIME()), df.format(service.END_TIME()));
                briefs += "\n";
            }
            return String.format(reply_allact, briefs);
        }
        boolean hasService = false;
        Date now = new Date();
        for (HGService service : getServices()) {
            if (now.after(service.START_TIME()) && now.before(service.END_TIME())) {
                hasService = true;
                break;
            }
        }
        if (!hasService) return reply_noact;
        String briefs = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        for (HGService service : getServices()) {
            if (now.after(service.START_TIME()) && now.before(service.END_TIME())) {
                briefs += String.format("【%s】\n", service.ENTRY());
                briefs += String.format("%s\n", service.BRIEF());
                briefs += String.format("%s - %s\n", df.format(service.START_TIME()), df.format(service.END_TIME()));
                briefs += "\n";
            }
        }
        return String.format(reply_act, briefs);
    }

    private EntryResult caseService(ReqWeChatXML xml, User user, Date now, HGService service) {
        if (xml.getContent().equals(service.ENTRY())) {
            if (now.before(service.START_TIME())) {
                return new EntryResult(true, createEarlyMsg(xml));
            } else if (now.after(service.END_TIME())) {
                return new EntryResult(true, createLateMsg(xml));
            } else {
                enterService(user, service.STATUS_ENTRY());
                return new EntryResult(true, null);
            }
        } else if (xml.getContent().equals(service.ENTRY_TEST())) {
            enterService(user, service.STATUS_ENTRY());
            return new EntryResult(true, null);
        }
        return new EntryResult(false, null);
    }
}
