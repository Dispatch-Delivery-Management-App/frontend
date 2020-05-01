package com.fullstack.frontend.ui.tracking;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import android.location.Location;
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


import com.google.android.gms.common.api.GoogleApiClient;
// import com.google.android.gms.location.LocationRequest;
import com.fullstack.frontend.ui.home.HomeFragment;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;


public class OrderDetailFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener  {

    private MapView mapView;
    private View v;
    double latitude;
    double longitude;
    List<OrderMapResponse.LatLong> first;
    List<OrderMapResponse.LatLong> second;

    double first_lat1, first_lng1, first_lat2, first_lng2;
    double second_lat1, second_lng1, second_lat2, second_lng2;
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
        int ID = OrderDetailFragmentArgs.fromBundle(getArguments()).getOrderId();
        TextView orderID = (TextView) v.findViewById(R.id.OrderID_filling);
        TextView createdTime = (TextView) v.findViewById(R.id.create_time_filling);
        TextView status = (TextView) v.findViewById(R.id.status_filling);
        TextView category = (TextView) v.findViewById(R.id.category_filling);
        TextView payment  = (TextView) v.findViewById(R.id.payment);
        TextView from_address = (TextView) v.findViewById(R.id.from_address_filling);
        TextView to_address = (TextView) v.findViewById(R.id.to_address_filling);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<OrderDetailResponse>> orderDetailResponse = apiService.postOrderDetail(new OrderDetailRequest(ID));
        orderDetailResponse.enqueue(new Callback<BaseResponse<OrderDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OrderDetailResponse>> call, retrofit2.Response<BaseResponse<OrderDetailResponse>> response) {
                if (response.isSuccessful()) {
                    OrderDetailResponse response1 = response.body().response;
                    orderID.setText(String.valueOf(response1.id));
                    String times = String.valueOf(response1.create_time);
                    String date = times.substring(0, 10);
                    String time = times.substring(11,19);
                    createdTime.setText(String.valueOf(date+" " + time));
                    if(response1.status == 1){
                        status.setText("Draft");
                    } else  if(response1.status == 2){
                        status.setText("NOT STARTED");
                    } if(response1.status == 2){
                        status.setText("SHIPPED");
                    } if(response1.status == 3){
                        status.setText("Completed");
                    }
                    category.setText(String.valueOf(response1.category));
                    payment.setText(String.valueOf(response1.totalCost));

                    //Log.d("test::::::::", String.valueOf(response1));
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

    //calling map api
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int ID = OrderDetailFragmentArgs.fromBundle(getArguments()).getOrderId();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<OrderMapResponse>> orderMapResponse = apiService.postOrderMap(new OrderMapRequest(ID));
        orderMapResponse.enqueue(new Callback<BaseResponse<OrderMapResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OrderMapResponse>> call, retrofit2.Response<BaseResponse<OrderMapResponse>> response) {
                if (response.isSuccessful()) {
                    OrderMapResponse response2 = response.body().response;
                    latitude = response2.tracking.lat;
                    longitude = response2.tracking.lng;
                    first = response2.get_first_part();
                    second = response2.get_second_part();
                    Log.d("test1:::", String.valueOf(latitude));
                }
            }
            public void onFailure(Call<BaseResponse<com.fullstack.frontend.Retro.OrderMapResponse>> call, Throwable t) {
            }
        });

        mapView = (MapView) view.findViewById(R.id.event_map_view);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        NavController navController = Navigation.findNavController(view);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.d("Test","BACKK");
                navController.navigate(R.id.detail_to_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),callback);
    }



    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        Log.d("test2:::", String.valueOf(latitude));

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)
        ).title("arrived");

//        MarkerOptions marker2 = new MarkerOptions().position(
//                new LatLng(first.get(1).lat, first.get(1).lng)
//        ).title("first");

        Log.d("map", String.valueOf(latitude));
        Log.d("map2", String.valueOf(longitude));

        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//        marker2.icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        googleMap.addMarker(marker);
//        googleMap.addMarker(marker2);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(2).build();

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


//        //add map route
//        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
//                .clickable(true)
//                .add(
//                        new LatLng( first_lat1, first_lng1),
//                        new LatLng( first_lat2, first_lng2)
//        ));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
//        googleMap.setOnPolylineClickListener(this);

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


    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
