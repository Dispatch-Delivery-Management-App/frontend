package com.fullstack.frontend.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;

    private LinearLayout shipOrder;
    private TextView status;
    private TextView orderId;
    private TextView category;
    private TextView receiver;

    private LinearLayout historyOrder;
    private TextView status2;
    private TextView orderId2;
    private TextView category2;
    private TextView receiver2;

    private LinearLayout draftOrder;
    private TextView category3;
    private TextView receiver3;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        status = root.findViewById(R.id.status);
        orderId = root.findViewById(R.id.order_id);
        category = root.findViewById(R.id.category);
        receiver = root.findViewById(R.id.receiver);

        status2 = root.findViewById(R.id.status2);
        orderId2 = root.findViewById(R.id.order_id2);
        category2 = root.findViewById(R.id.category2);
        receiver2 = root.findViewById(R.id.receiver2);

        category3 = root.findViewById(R.id.category3);
        receiver3 = root.findViewById(R.id.receiver2);




        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<List<OrderResponse>>> getOrders = apiService.getOrderList(new OrderDetailRequest(1));
        getOrders.enqueue(new Callback<BaseResponse<List<OrderResponse>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<OrderResponse>>> call, Response<BaseResponse<List<OrderResponse>>> response) {
                if (response.isSuccessful()) {
                    List<OrderResponse> orderResponses = response.body().response;
                    for (OrderResponse response1 :orderResponses) {
                        Log.d("test ", response1.category + response1.lastname);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<OrderResponse>>> call, Throwable t) {

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // the button for going to place a new order page
        FloatingActionButton order = getActivity().findViewById(R.id.btn_order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager()
////                        .beginTransaction()
////                        .replace(R.id.content_main, PlaceOrderFragment.newInstance())
////                        .commit();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_home_to_nav_placeOrder);

            }
        });

        // Button for shipping order details
        Button detail1 = getActivity().findViewById(R.id.btn_detail);
        // Button for history order details
        Button detail2 = getActivity().findViewById(R.id.btn_detail2);
        // Button for draft box details
        Button detail3 = getActivity().findViewById(R.id.btn_detail3);

        detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_detail);
            }
        });

        detail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_detail);
            }
        });

        detail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_detail);
            }
        });

    }







}
