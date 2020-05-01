package com.fullstack.frontend.Retro.newOrder;

import android.os.Parcel;
import android.os.Parcelable;

public class Plan implements Parcelable {
    public int type;
    public int fee;
    public double duration;
    public String shipping_method;//0,1{drone,robot}
    public int amount;
    public int station;
    public double rating;//out of 5

    protected Plan(Parcel in) {
        type = in.readInt();
        fee = in.readInt();
        duration = in.readDouble();
        shipping_method = in.readString();
        amount = in.readInt();
        station = in.readInt();
        rating = in.readDouble();
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(fee);
        dest.writeDouble(duration);
        dest.writeString(shipping_method);
        dest.writeInt(amount);
        dest.writeInt(station);
        dest.writeDouble(rating);
    }
}
