package com.croxx.hgwechat.res.didiumbrella;

import com.croxx.hgwechat.model.didiumbrella.DidiUmbrellaOrder;

import java.util.List;

public class ResDidiUmbrellaWithOrders extends ResDidiUmbrella {
    private List<DidiUmbrellaOrder> orders;

    public ResDidiUmbrellaWithOrders() {
    }

    public ResDidiUmbrellaWithOrders(int errcode, String errmsg, List<DidiUmbrellaOrder> orders) {
        super(errcode, errmsg);
        this.orders = orders;
    }

    /*    Getters & Setters     */

    public List<DidiUmbrellaOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<DidiUmbrellaOrder> orders) {
        this.orders = orders;
    }
}
