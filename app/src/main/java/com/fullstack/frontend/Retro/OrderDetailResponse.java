package com.fullstack.frontend.Retro;
import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderDetailResponse {
//    public OrderDetailResponse(){
//    }

    public Long id;
    public Long User_id;
    public Long from_address;
    public Long to_address;
    public int status;
    public Long tracking_id;
    public String category;
    public String item_info;
    public Double capacity;
    public String create_time;
    public String depart_time;
    public String pickup_time;
    public String complete_time;
    public Long station_id;
    public String shipping_method;
    public int shipping_member;
    public Double totalCost;
    public Integer feedback;

    public Long getId(){
        return id;
    }

    public Long getUser_id(){
        return User_id;
    }

    public Long get_from_address(){
        return from_address;
    }

    public Long get_to_address(){
        return to_address;
    }

    public Long get_tracking_id(){
        return tracking_id;
    }

    public Long get_station_id(){
        return station_id;
    }

    public Integer get_status(){
        return status;
    }

    public Integer get_shipping_member(){
        return shipping_member;
    }
    public Integer get_feedback(){
        return feedback;
    }
    public Double get_capacity(){
        return capacity;
    }
    public Double get_totalCost(){
        return totalCost;
    }
    public String get_category(){
        return category;
    }
    public String get_item_info(){
        return item_info;
    }
    public String get_create_time(){
        return create_time;
    }
    public String get_depart_time(){
        return depart_time;
    }
    public String get_pickup_time(){
        return pickup_time;
    };
    public String get_complete_time(){
        return complete_time;
    };

    public String get_shipping_method(){
        return shipping_method;
    }

}

