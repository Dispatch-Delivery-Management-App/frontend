package com.fullstack.frontend.ui.tracking;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fullstack.frontend.R;
import android.widget.Button;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderDetailResponse;
import com.fullstack.frontend.Retro.Response;
//import com.fullstack.frontend.databinding.OrderDetailFragmentBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;



public class OrderDetailFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private View view;
    //private OrderDetailViewModel detailViewModel;
    //private OrderDetailFragmentBinding view;
//    private OrderDetailFragmentBinding binding;
    public OrderDetailFragment(){

    }


    public static OrderDetailFragment newInstance(Response response){
        Bundle args = new Bundle();
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_detail_fragment, container,
                false);
        return view;
    }


//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        //view = OrderDetailFragmentBinding.inflate(R.layout.order_detail_fragment, container, false);
//        view = inflater.inflate(R.layout.order_detail_fragment, container, false);
//
////        detailViewModel = ViewModelProviders.of(this).get(OrderDetailViewModel.class);
////        View root  = inflater.inflate(R.layout.orderdetail_fragment, container, false );
//
//        //return binding.getRoot();
//        return view;
//    }



    // call API
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.event_map_view);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();// needed to get the map to display immediately
            mapView.getMapAsync(this);


        }


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<OrderDetailResponse> OrderDetailResponse = apiService.postOrderDetail(new OrderDetailRequest(2));


        OrderDetailResponse.enqueue(new Callback<OrderDetailResponse>() {

            @Override
            public void onResponse(Call<com.fullstack.frontend.Retro.OrderDetailResponse> call, retrofit2.Response<com.fullstack.frontend.Retro.OrderDetailResponse> response) {
                if (response.isSuccessful()) {
                    OrderDetailResponse Body = response.body();
                    Log.d("test:::::", String.valueOf(Body));
                }
            }

            @Override
            public void onFailure(Call<com.fullstack.frontend.Retro.OrderDetailResponse> call, Throwable t) {

            }

        });


        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Log.d("Test","BACKK");
                navController.navigate(R.id.detail_to_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),callback);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    public void onMapReady(GoogleMap googleMap) {

    }





}
