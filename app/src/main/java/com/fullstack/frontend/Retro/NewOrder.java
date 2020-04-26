package com.fullstack.frontend.Retro;

import com.google.gson.annotations.SerializedName;

public class NewOrder {
    public Integer id;
    public int userId;

    @SerializedName("fromFirstname")
    public String fromFirstname;
    @SerializedName("fromLastname")
    public String fromLastname;
    @SerializedName("fromAddress")
    public String fromAddress;
    @SerializedName("toFirstname")
    public String toFirstname;
    @SerializedName("toLastname")
    public String toLastname;
    @SerializedName("toAddress")
    public String toAddress;

    public NewOrder(int userId, String fromFirstname, String fromLastname, String fromAddress, String toFirstname, String toLastname, String toAddress) {
        this.userId = userId;
        this.fromFirstname = fromFirstname;
        this.fromLastname = fromLastname;
        this.fromAddress = fromAddress;
        this.toFirstname = toFirstname;
        this.toLastname = toLastname;
        this.toAddress = toAddress;
    }
}
