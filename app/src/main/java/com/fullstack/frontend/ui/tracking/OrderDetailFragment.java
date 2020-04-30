package com.fullstack.frontend.ui.tracking;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fullstack.frontend.R;
import android.widget.Button;
import android.widget.Toast;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderDetailResponse;
import com.fullstack.frontend.Retro.OrderMapRequest;
import com.fullstack.frontend.Retro.OrderMapResponse;
import com.fullstack.frontend.Retro.Response;
import com.fullstack.frontend.databinding.OrderDetailFragmentBinding;
import com.fullstack.frontend.ui.home.HomeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.BreakIterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;


public class OrderDetailFragment extends Fragment implements OnMapReadyCallback {


    private MapView mapView;
    private View v;
    double latitude;
    double longitude;
    //private OrderDetailFragmentBinding binding;
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

        View v =  inflater.inflate(R.layout.order_detail_fragment, container,
                false);
        TextView orderID = (TextView) v.findViewById(R.id.OrderID_filling);
        TextView createdTime = (TextView) v.findViewById(R.id.create_time_filling);
        TextView status = (TextView) v.findViewById(R.id.status_filling);
        TextView category = (TextView) v.findViewById(R.id.category_filling);
        TextView payment  = (TextView) v.findViewById(R.id.payment);
        TextView from_address = (TextView) v.findViewById(R.id.from_address_filling);
        TextView to_address = (TextView) v.findViewById(R.id.to_address_filling);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<OrderMapResponse>> orderMapResponse = apiService.postOrderMap(new OrderMapRequest(2));
        orderMapResponse.enqueue(new Callback<BaseResponse<OrderMapResponse>>() {

            @Override
            public void onResponse(Call<BaseResponse<OrderMapResponse>> call, retrofit2.Response<BaseResponse<OrderMapResponse>> response) {
                if (response.isSuccessful()) {
                    OrderMapResponse response2 = response.body().response;

                    latitude = response2.tracking.lat;
                    longitude = response2.tracking.lng;

                }

            }

            public void onFailure(Call<BaseResponse<com.fullstack.frontend.Retro.OrderMapResponse>> call, Throwable t) {


            }
        });


        Call<BaseResponse<OrderDetailResponse>> orderDetailResponse = apiService.postOrderDetail(new OrderDetailRequest(1));
        orderDetailResponse.enqueue(new Callback<BaseResponse<OrderDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OrderDetailResponse>> call, retrofit2.Response<BaseResponse<OrderDetailResponse>> response) {
                if (response.isSuccessful()) {
                    OrderDetailResponse response1 = response.body().response;
                    Log.d("test:::::", String.valueOf(response1.category));

                    orderID.setText(String.valueOf(response1.id));
                    createdTime.setText(String.valueOf(response1.create_time));
                    status.setText(String.valueOf(response1.status));
                    category.setText(String.valueOf(response1.category));
                    payment.setText(String.valueOf(response1.totalCost));
                    from_address.setText(String.valueOf(response1.from_address));
                    to_address.setText(String.valueOf(response1.to_address));

                }
            }

            @Override
            public void onFailure(Call<BaseResponse<com.fullstack.frontend.Retro.OrderDetailResponse>> call, Throwable t) {
                Log.d("test:::", "failed to load from server");
            }
        });


        return v;
    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.event_map_view);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();// needed to get the map to display immediately
            mapView.getMapAsync(this);
        }


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


    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());


// Create marker on google map
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("arrived");


        // Change marker Icon on google map
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // Add marker to google map
        googleMap.addMarker(marker);


        // Set up camera configuration, set camera to lat and lng, and set Zoom to 12
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();

        // Animate the zoom process
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
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








}
