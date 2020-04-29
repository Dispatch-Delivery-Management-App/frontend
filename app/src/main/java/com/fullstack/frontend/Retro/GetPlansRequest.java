package com.fullstack.frontend.Retro;

public class GetPlansRequest {
    public int user_id;
    public NewOrderSubAddress fromAddress = new NewOrderSubAddress();
    public NewOrderSubAddress toAddress = new NewOrderSubAddress();
    public double packageWeight;
    public String packageCategory;
    public String MMDD;//e.g. 23-04-2020
    public String startSlot;//e.g.14 => 14:00 - 16:00
    public String item_info;
    public int station;
    public int tracking;
}
