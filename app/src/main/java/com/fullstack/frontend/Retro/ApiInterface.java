package com.fullstack.frontend.Retro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    @GET("login/{username}/{password}")
//    Call login(@Path("username") String username, @Path("password") String password);

    @GET("/todos")
    Call<List<Todo>> getTodos();
//
//    @GET("/todos/{id}")
//    Call<Todo> getPost(@Path("id") int id);
//
//    @GET("/todos")
//    Call<Todo> getTodoUsingQuery(@Query("id") int id);

    @POST("orders")
    Call<NewOrder> postOrderGetPlans2(@Body NewOrder newOrder);

    @POST("/api/orderlist/")
    Call<OrderMainResponse> postOrderGetPlans(@Body OrderDetailRequest orderDetailRequest);

}
