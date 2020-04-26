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
import com.fullstack.frontend.databinding.OrderDetailFragmentBinding;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class OrderDetailFragment extends Fragment{
    private OrderDetailFragmentBinding binding;
    public OrderDetailFragment(){

    }
    private OrderDetailViewModel detailViewModel;
    public static OrderDetailFragment newInstance(Response response){
        Bundle args = new Bundle();
        OrderDetailFragment fragment = new OrderDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //button
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = OrderDetailFragmentBinding.inflate(inflater, container, false);
//        detailViewModel = ViewModelProviders.of(this).get(OrderDetailViewModel.class);
//        View root  = inflater.inflate(R.layout.orderdetail_fragment, container, false );

        return binding.getRoot();
    }


    // call API
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<OrderDetailResponse> OrderDetailResponse = apiService.postOrderDetail(new OrderDetailRequest());


        OrderDetailResponse.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<com.fullstack.frontend.Retro.OrderDetailResponse> call, retrofit2.Response<com.fullstack.frontend.Retro.OrderDetailResponse> response) {
                if(response.isSuccessful()){
                    OrderDetailResponse orderResponse = response.body();
                    Log.d("test",orderResponse.complete_time);
                }
            }

            @Override
            public void onFailure(Call<com.fullstack.frontend.Retro.OrderDetailResponse> call, Throwable t) {
                Log.d("onResponse: None","1");

            }


//                //binding.placeAt.setText();


        });


        super.onViewCreated(view, savedInstanceState);
//        Button map = getActivity().findViewById(R.id.detail_map);
//        map.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                Navigation.findNavController(view).navigate(R.id.detail_map);
//            }
//        });
//
//        Button back = getActivity().findViewById(R.id.detail_to_home);
//        back.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                Navigation.findNavController(view).navigate(R.id.nav_home);
//            }
//
//        });

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


    //    @Override
    //    protected OrderDetailViewModel getViewModel() {
    //        return new ViewModelProvider(requireActivity(), getFactory()).get(OrderDetailViewModel.class);
    //
    //    }
    //
    //    @Override
    //    protected ViewModelProvider.Factory getFactory() {
    //        return new ViewModelProvider.Factory() {
    //            @NonNull
    //            @Override
    //            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    //                return (T) new OrderDetailViewModel(getRepository());
    //            }
    //        };
    //
    //    }
    //
    //    @Override
    //    protected OrderDetailRepository getRepository() {
    //        return null;
    //    }
}
