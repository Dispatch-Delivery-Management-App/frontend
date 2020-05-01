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


    public static OrderDetailFragment newInstance(Response response){
        Bundle args = new Bundle();
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }



    double first_lat1, first_lng1, first_lat2, first_lng2;
    double second_lat1, second_lng1, second_lat2, second_lng2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.order_detail_fragment, container,
                false);
        int order_ID = OrderDetailFragmentArgs.fromBundle(getArguments()).getOrderId();



        TextView orderID = (TextView) v.findViewById(R.id.OrderID_filling);
        TextView createdTime = (TextView) v.findViewById(R.id.create_time_filling);
        TextView status = (TextView) v.findViewById(R.id.status_filling);
        TextView category = (TextView) v.findViewById(R.id.category_filling);
        TextView payment  = (TextView) v.findViewById(R.id.payment);
        TextView from_address = (TextView) v.findViewById(R.id.from_address_filling);
        TextView to_address = (TextView) v.findViewById(R.id.to_address_filling);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse<OrderMapResponse>> orderMapResponse = apiService.postOrderMap(new OrderMapRequest(order_ID));
        orderMapResponse.enqueue(new Callback<BaseResponse<OrderMapResponse>>() {

            @Override
            public void onResponse(Call<BaseResponse<OrderMapResponse>> call, retrofit2.Response<BaseResponse<OrderMapResponse>> response) {
                if (response.isSuccessful()) {
                    OrderMapResponse response2 = response.body().response;
                    latitude = response2.tracking.lat;
                    longitude = response2.tracking.lng;

                    first = response2.first_part;
                    second = response2.second_part;
                    int a = first.size();

                    Iterator iterator = first.iterator();
//                    while(iterator.hasNext()) {
//                        double l = iterator.next().;
//                        Log.d("test:::::", String.valueOf(l));
//                    }


                }

            }

            public void onFailure(Call<BaseResponse<com.fullstack.frontend.Retro.OrderMapResponse>> call, Throwable t) {


            }
        });


        Call<BaseResponse<OrderDetailResponse>> orderDetailResponse = apiService.postOrderDetail(new OrderDetailRequest(order_ID));
        orderDetailResponse.enqueue(new Callback<BaseResponse<OrderDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OrderDetailResponse>> call, retrofit2.Response<BaseResponse<OrderDetailResponse>> response) {
                if (response.isSuccessful()) {
                    OrderDetailResponse response1 = response.body().response;
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

        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(

                        new LatLng( first_lat1,  first_lng1),
                        new LatLng(first_lat2, first_lng2),
                        new LatLng(second_lat1,second_lng1),
                        new LatLng(second_lat2, second_lng2)
        ));


        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
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
