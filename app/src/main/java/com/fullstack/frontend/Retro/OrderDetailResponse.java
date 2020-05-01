package com.fullstack.frontend.Retro;

public class OrderDetailResponse {
    public OrderDetailResponse(int id, int status, String category){
        this.id = id;
        this.status = status;
        this.category = category;
    }

    public long id;
    public long user_id;

    public String from_street;
    public String from_city;
    public String from_state;
    public int from_zipcode;

    public String to_street;
    public String to_city;
    public String to_state;
    public int to_zipcode;


    public int status;
    public long tracking_id;
    public String category;
    public String item_info;
    public double capacity;
    public String create_time;
    public String depart_time;
    public String pickup_time;
    public String complete_time;
    public long station_id;
    public String shipping_method;
    public int shipping_member;
    public double total_cost;
    public int feedback;

//    public long get_id(){
//        return id;
//    }
//
//    public long get_User_id(){
//        return User_id;
//    }
//
//    public long get_from_address(){
//        return from_address;
//    }
//
//    public long get_to_address(){
//        return to_address;
//    }
//
//    public long get_tracking_id(){
//        return tracking_id;
//    }
//
//    public long get_station_id(){
//        return station_id;
//    }
//
//    public int get_status(){
//        return status;
//    }
//
//    public int get_shipping_member(){
//        return shipping_member;
//    }
//    public int get_feedback(){
//        return feedback;
//    }
//    public double get_capacity(){
//        return capacity;
//    }
//    public double get_totalCost(){
//        return totalCost;
//    }
//    public String get_category(){
//        return category;
//    }
//    public String get_item_info(){
//        return item_info;
//    }
//    public String get_create_time(){
//        return create_time;
//    }
//    public String get_depart_time(){
//        return depart_time;
//    }
//    public String get_pickup_time(){
//        return pickup_time;
//    };
//    public String get_complete_time(){
//        return complete_time;
//    };
//
//    public String get_shipping_method(){
//        return shipping_method;
//    }

}


