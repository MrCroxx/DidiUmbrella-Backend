package com.croxx.hgwechat.res.didiumbrella;

import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaOrder;

public class ResDidiUmbrellaWithOrder extends ResDidiUmbrella {
    private DidiUmbrellaOrder order;

    public ResDidiUmbrellaWithOrder() {
    }

    public ResDidiUmbrellaWithOrder(int errcode, String errmsg, DidiUmbrellaOrder order) {
        super(errcode, errmsg);
        this.order = order;
    }

    /*    Getters & Setters     */

    public DidiUmbrellaOrder getOrder() {
        return order;
    }

    public void setOrder(DidiUmbrellaOrder order) {
        this.order = order;
    }
}
