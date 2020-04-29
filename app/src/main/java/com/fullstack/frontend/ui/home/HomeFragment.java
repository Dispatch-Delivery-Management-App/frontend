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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private View root;
    private RecyclerView mOrderListRV;
    private OrderListAdapter mOrderListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this, new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new HomeViewModel(new OrderListRepository());
                    }
                }).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.setOrderRequest(new OrderDetailRequest(1));

        initRecyclerView();

        return root;
    }


    private void initRecyclerView() {
        // get RecyclerView
        mOrderListRV = root.findViewById(R.id.rv_order_list);
        // build adapter
        mOrderListAdapter = new OrderListAdapter();
        // set adapter for RecyclerView
        mOrderListRV.setAdapter(mOrderListAdapter);
        // set layoutManager
        mOrderListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        // item listener
        mOrderListAdapter.setOnItemClickListener(view -> Navigation.findNavController(view).navigate(R.id.nav_detail));
        homeViewModel.getOrders().observe(getViewLifecycleOwner(), orderResponses -> {
            mOrderListAdapter.setOrders(orderResponses);
        });
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

//        // Button for order details
//        Button detail = getActivity().findViewById(R.id.btn_detail);
//
//        detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.nav_detail);
//            }
//        });
//
    }


}
