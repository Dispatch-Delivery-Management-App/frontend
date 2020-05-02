package com.fullstack.frontend.Retro.newOrder;

import java.io.Serializable;

public class GetPlansRequest implements Serializable {
    public int user_id;
    public NewOrderSubAddress fromAddress = new NewOrderSubAddress();
    public NewOrderSubAddress toAddress = new NewOrderSubAddress();
    public double packageWeight;
    public String packageCategory;
    public String MMDD;//e.g. 23-04-2020
    public String startSlot;//e.g.14 => 14:00 - 16:00
    public String item_info;
    public int order_status=2;//2 normal, 1 draft


    public String shipping_method;
    public int amount;
    public int station;
    public double fee;
}
