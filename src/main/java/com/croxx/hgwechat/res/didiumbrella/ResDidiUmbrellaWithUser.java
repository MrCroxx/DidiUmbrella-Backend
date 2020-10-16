package com.croxx.hgwechat.res.didiumbrella;

import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaUser;

public class ResDidiUmbrellaWithUser extends ResDidiUmbrella {
    private DidiUmbrellaUser user;

    public ResDidiUmbrellaWithUser() {
    }

    public ResDidiUmbrellaWithUser(int errcode, String errmsg, DidiUmbrellaUser user) {
        super(errcode, errmsg);
        this.user = user;
    }

    /*    Getters & Setters     */

    public DidiUmbrellaUser getUser() {
        return user;
    }

    public void setUser(DidiUmbrellaUser user) {
        this.user = user;
    }
}
