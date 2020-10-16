package com.croxx.hgwechat.service;

import com.croxx.hgwechat.model.a2.A2;
import com.croxx.hgwechat.model.a2.A2Repository;
import com.croxx.hgwechat.model.user.User;
import com.croxx.hgwechat.model.user.UserRepository;
import com.croxx.hgwechat.req.ReqWeChatXML;
import com.croxx.hgwechat.res.ResWeChatMsg;
import com.croxx.hgwechat.res.ResWeChatTextMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class A2Service extends HGService {

    @Override
    public String ENTRY() {
        return "表白";
    }

    @Override
    public String ENTRY_TEST() {
        return "A2表白";
    }

    @Override
    public String BRIEF() {
        return "与你的第一次小概率事件碰撞。";
    }

    @Override
    public Date START_TIME() {
        return new GregorianCalendar(2018, 4, 17, 0, 0, 0).getTime();
    }

    // 这个弱智GregorianCalendar月份是0-11
    @Override
    public Date END_TIME() {
        return new GregorianCalendar(2018, 5, 1, 0, 0, 0).getTime();
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
        ENTRY("A2_ENTRY"),
        WRITE_CONTENT("A2_WRITE_CONTENT"),
        WRITE_TO("A2_WRITE_TO"),
        WRITE_FROM("A2_WRITE_FROM"),
        CONFIRM("A2_CONFIRM");

        private final String text;

        private STATUS(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }

        public static boolean has(String str) {
            for (STATUS status : STATUS.values()) {
                if (status.toString().equals(str)) return true;
            }
            return false;
        }
    }

    @Value("${reply.a2.notfound}")
    private String reply_notfound;
    @Value("${reply.a2.found}")
    private String reply_found;
    @Value("${reply.a2.writecontent}")
    private String reply_writecontent;
    @Value("${reply.a2.writefrom}")
    private String reply_writefrom;
    @Value("${reply.a2.writeto}")
    private String reply_writeto;
    @Value("${reply.a2.confirm}")
    private String reply_confirm;
    @Value("${reply.a2.commit}")
    private String reply_commit;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private A2Repository a2Repository;
    @Autowired
    private WeChatService weChatService;

    @Override
    public ResWeChatMsg handle(ReqWeChatXML xml, User user, String serviceStatus) {
        if (serviceStatus.equals(STATUS.ENTRY.toString())) {
            return handleENTRY(xml, user);
        } else if (serviceStatus.equals(STATUS.WRITE_TO.toString())) {
            return handleWRITE_TO(xml, user);
        } else if (serviceStatus.equals(STATUS.WRITE_CONTENT.toString())) {
            return handleWRITE_CONTENT(xml, user);
        } else if (serviceStatus.equals(STATUS.WRITE_FROM.toString())) {
            return handleWRITE_FROM(xml, user);
        } else if (serviceStatus.equals(STATUS.CONFIRM.toString())) {
            return handleCONFIRM(xml, user);
        }
        return weChatService.handleERROR(xml, user);
    }

    private ResWeChatMsg handleENTRY(ReqWeChatXML xml, User user) {
        A2 a2 = a2Repository.findByOpenidAndStatus(user.getOpenID(), A2.STATUS_OK);
        user.updateServiceStatus(WeChatService.SERVICE_STATUS_EXPIRE);
        userRepository.save(user);
        if (a2 != null) {
            // 已表白
            List<A2> results = a2Repository.findByFromidOrFromnameAndStatus(a2.getToid(), a2.getToname(), A2.STATUS_OK);
            A2 a2FromLover = null;
            for (A2 a : results) {
                if ((a2.getToid().equals(a.getFromid()) && a2.getFromid().equals(a.getToid()) && !a2.getToid().equals("")&& !a.getToid().equals("")) ||
                        (a2.getToname().equals(a.getFromname()) && a2.getFromname().equals(a.getToname()) && !a2.getToname().equals("")&& !a.getToname().equals(""))) {
                    a2FromLover = a;
                    break;
                }
            }
            if (a2FromLover != null) {
                // 匹配
                return new ResWeChatTextMsg(
                        xml.getFromUserName(), xml.getToUserName(),
                        WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, fillNotice(reply_found, a2FromLover)
                );
            } else {
                // 未匹配
                return new ResWeChatTextMsg(
                        xml.getFromUserName(), xml.getToUserName(),
                        WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_notfound
                );
            }
        } else {
            // 未表白
            user.updateServiceStatus(STATUS.WRITE_TO.toString());
            userRepository.save(user);
            return new ResWeChatTextMsg(
                    xml.getFromUserName(), xml.getToUserName(),
                    WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_writeto);
        }
    }

    private ResWeChatMsg handleWRITE_TO(ReqWeChatXML xml, User user) {
        A2 a2 = a2Repository.findByOpenid(xml.getFromUserName());
        if (a2 == null) a2 = new A2();
        a2.setOpenid(xml.getFromUserName());
        a2.setStatus(A2.STATUS_EDITING);
        Pattern p_id = Pattern.compile("\\d*");
        //Pattern p_name = Pattern.compile("\\D*");
        String id = "";
        String name = "";
        String content = xml.getContent();
        Matcher m_id = p_id.matcher(content);
        //Matcher m_name = p_name.matcher(content);
        if (m_id.find()) id = m_id.group();
        //if (m_name.find()) name = m_name.group();
        content = content.replace(id, "");
        name = content.replaceAll(" ", "");
        a2.setToid(id);
        a2.setToname(name);
        a2Repository.save(a2);
        user.updateServiceStatus(STATUS.WRITE_CONTENT.toString());
        userRepository.save(user);
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(),
                WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_writecontent);
    }

    private ResWeChatMsg handleWRITE_CONTENT(ReqWeChatXML xml, User user) {
        A2 a2 = a2Repository.findByOpenid(xml.getFromUserName());
        if (a2 == null) return weChatService.handleERROR(xml, user);
        a2.setContent(xml.getContent());
        a2Repository.save(a2);
        user.updateServiceStatus(STATUS.WRITE_FROM.toString());
        userRepository.save(user);
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(),
                WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_writefrom);
    }

    private ResWeChatMsg handleWRITE_FROM(ReqWeChatXML xml, User user) {
        A2 a2 = a2Repository.findByOpenid(xml.getFromUserName());
        if (a2 == null) return weChatService.handleERROR(xml, user);
        Pattern p_id = Pattern.compile("\\d*");
        //Pattern p_name = Pattern.compile("\\D*");
        String id = "";
        String name = "";
        String content = xml.getContent();
        Matcher m_id = p_id.matcher(content);
        //Matcher m_name = p_name.matcher(content);
        if (m_id.find()) id = m_id.group();
        //if (m_name.find()) name = m_name.group();
        content = content.replace(id, "");
        name = content.replaceAll(" ", "");
        a2.setFromid(id);
        a2.setFromname(name);
        a2Repository.save(a2);
        user.updateServiceStatus(STATUS.CONFIRM.toString());
        userRepository.save(user);
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(),
                WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, fillNotice(reply_confirm, a2));
    }

    private ResWeChatMsg handleCONFIRM(ReqWeChatXML xml, User user) {
        A2 a2 = a2Repository.findByOpenid(xml.getFromUserName());
        if (a2 == null) return weChatService.handleERROR(xml, user);
        if (xml.getContent().equals("重写")) {
            user.updateServiceStatus(STATUS.WRITE_TO.toString());
            userRepository.save(user);
            return new ResWeChatTextMsg(
                    xml.getFromUserName(), xml.getToUserName(),
                    WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_writeto);
        }
        if (xml.getContent().equals("确认")) {
            a2.setStatus(A2.STATUS_OK);
            a2Repository.save(a2);
            user.updateServiceStatus(WeChatService.SERVICE_STATUS_EXPIRE);
            userRepository.save(user);
            return new ResWeChatTextMsg(
                    xml.getFromUserName(), xml.getToUserName(),
                    WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, reply_commit);
        }
        return new ResWeChatTextMsg(
                xml.getFromUserName(), xml.getToUserName(),
                WeChatService.getCreateTime(), WeChatService.TYPE_TEXT, fillNotice(reply_confirm, a2));
    }

    private String fillNotice(String format, A2 a2) {
        return String.format(format, a2.getToid(), a2.getToname(), a2.getContent(), a2.getFromid(), a2.getFromname());
    }

}
