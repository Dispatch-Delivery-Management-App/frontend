package com.fullstack.frontend.Retro;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OrderMapResponse {

    public class LatLong{
        public LatLong(double lat, double lng, List<LatLong> first_part, List<LatLong> second_part){
            this.lat = lat;
            this.lng = lng;

        }
        public double lat;
        public double lng;

        public double get_lat(){
            return lat;
        }
        public double get_lon(){
            return lng;
        }
    }
    public List<LatLong> first_part;
    public List<LatLong> second_part;
    public LatLong tracking;

    public List<LatLong> get_first_part(){
        return first_part;
    }

    public List<LatLong> get_second_part(){
        return second_part;
    }




}


