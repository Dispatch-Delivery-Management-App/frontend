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

    @POST("/api/user-login/")
    Call<BaseResponse<OnBoardingResponse>> login(@Body OnBoardingResponse credential);

    @POST("/api/user-signup/")
    Call<BaseResponse<OnBoardingResponse>> register(@Body OnBoardingResponse credential);

    @POST("/api/orderplan/")
    Call<BaseResponse<List<Plan>>> postOrderGetPlans(@Body GetPlansRequest getPlansRequest);


    @POST("/api/orderlist/")
    Call<BaseResponse<List<OrderResponse>>> getOrderList(@Body OrderListRequest orderListRequest);

    @POST("/api/orderdetail/")
    Call<BaseResponse<OrderDetailResponse>> postOrderDetail(@Body OrderDetailRequest orderDetailRequest);

    @POST("/api/map/")
    Call<BaseResponse<OrderMapResponse>> postOrderMap(@Body OrderMapRequest orderMapRequest);

    @POST("/api/token/")
    Call<BaseResponse> postToken(@Body TokenRequest tokenRequest);


    @POST("/api/placeorder/")
    Call<BaseResponse<String>> confirmOrder(@Body GetPlansRequest confirmOrderRequest);


    @POST("/api/search/")
    Call<BaseResponse<List<SearchResponse>>> search(@Body SearchRequest searchRequest);

}
