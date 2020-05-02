package com.fullstack.frontend.Retro;

import com.fullstack.frontend.Retro.newOrder.AddressListRequest;
import com.fullstack.frontend.Retro.newOrder.AddressResponse;
import com.fullstack.frontend.Retro.newOrder.GetPlansRequest;
import com.fullstack.frontend.Retro.newOrder.Plan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
    Call<BaseResponse<Integer>> confirmOrder(@Body GetPlansRequest confirmOrderRequest);


    @POST("/api/search/")
    Call<BaseResponse<List<SearchResponse>>> search(@Body SearchRequest searchRequest);

    @POST("/api/address-list/")
    Call<BaseResponse<List<AddressResponse>>> getAddressList(@Body AddressListRequest addressListRequest);

    @POST("/api/feedback/")
    Call<BaseResponse<String>> goRating(@Body RatingRequest request);
}
