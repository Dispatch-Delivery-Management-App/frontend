package com.fullstack.frontend.ui.tracking;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
    MutableLiveData<Double> liveLAT = new MutableLiveData<>();
    MutableLiveData<Double> liveLONG = new MutableLiveData<>();
    List<OrderMapResponse.LatLong> first;
    List<OrderMapResponse.LatLong> second;
    
    double[] lat_list;
    double[] lng_list;



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
                    payment.setText(String.valueOf(response1.total_cost));
                    String fromAddress = response1.from_street + '\n' + response1.from_city +   '\n' + response1.from_state + " " +String.valueOf(response1.from_zipcode);
                    from_address.setText(fromAddress);
                    String toAddress = response1.to_street + '\n' + response1.to_city +   '\n' + response1.to_state + " " + String.valueOf(response1.to_zipcode);
                    to_address.setText(toAddress);

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
                    first = response2.first_part;
                    second = response2.second_part;

                    liveLAT.postValue(response2.tracking.lat);
                    liveLONG.postValue(response2.tracking.lng);

                    lat_list = new double[first.size() + second.size()];
                    lng_list = new double[first.size() + second.size()];


                    int j = 0;
                    for(int i = 0; i < first.size() + second.size(); i++){
                        if(i < first.size()){
                                lat_list[i] = first.get(i).lat;
                                lng_list[i] = first.get(i).lng;
                        } else{
                                lat_list[i] = second.get(j).lat;
                                lng_list[i] = second.get(j).lng;
                                j++;
                        }
                    }



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

        MutableLiveData<Integer> both = new MutableLiveData<>();
        both.setValue(0);
        Log.d("test2:::", String.valueOf(latitude));
        Log.d("test3:::", liveLAT.toString());
        liveLAT.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Log.d("test3:::", liveLAT.getValue().toString());
                int cur = both.getValue();
                both.setValue(cur+1);
            }
        });

        liveLONG.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Log.d("test4:::", liveLAT.getValue().toString());
                int cur = both.getValue();
                both.setValue(cur+1);
            }
        });

        both.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (both.getValue()==2){
                    Log.d("test5:::", liveLAT.getValue().toString());
                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(liveLAT.getValue(), liveLONG.getValue())
                    ).title("TRACKING");

//                    MarkerOptions fromMarker = new MarkerOptions().position(
//                        new LatLng(a,b)
//                    ).title("FROM");

                     marker.icon(BitmapDescriptorFactory
                             .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

//                 //   fromMarker.icon(BitmapDescriptorFactory
//                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                     googleMap.addMarker(marker);
//                     googleMap.addMarker(fromMarker);


                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(latitude, longitude)).zoom(10).build();

                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));


                    PolylineOptions line = new PolylineOptions();
                    line.add(new LatLng( latitude , longitude ));
                    for(int i = 0; i < first.size() + second.size(); i++){
                        line.add(new LatLng( lat_list[i] , lng_list[i]));
                    }


                    Polyline polyline = googleMap.addPolyline(line);
                    polyline.setClickable(true);
                }


            }
        });

//        Log.d("map", String.valueOf(latitude));
//        Log.d("map2", String.valueOf(longitude));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude,longitude)).zoom(10).build();

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 10));
        googleMap.setOnPolylineClickListener(this);

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
