package com.fullstack.frontend.Retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/api/user-signup/")
    @FormUrlEncoded
    Call<BaseResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password);

    @POST("/api/user-login/")
    @FormUrlEncoded
    Call<BaseResponse> login(
            @Field("username") String username,
            @Field("password") String password);

    @POST("/api/placeorder/")
    Call<BaseResponse<String>> postOrderGetPlans(@Body GetPlansRequest getPlansRequest);

    @POST("/api/orderlist/")
    Call<BaseResponse<List<OrderResponse>>> getOrderList(@Body OrderDetailRequest orderDetailRequest);

}
