package com.croxx.hgwechat.service;

import com.croxx.hgwechat.model.user.User;
import com.croxx.hgwechat.req.ReqWeChatXML;
import com.croxx.hgwechat.res.ResWeChatMsg;

import java.util.Date;

public abstract class HGService {

    public abstract String ENTRY();

    public abstract String ENTRY_TEST();

    public abstract String BRIEF();

    public abstract Date START_TIME();

    public abstract Date END_TIME();

    public abstract String STATUS_ENTRY();

    public abstract boolean hasServiceStatus(String serviceStatus);

    public abstract ResWeChatMsg handle(ReqWeChatXML xml, User user, String serviceStatus);
}
