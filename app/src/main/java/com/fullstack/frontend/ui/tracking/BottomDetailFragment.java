package com.fullstack.frontend.ui.tracking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderDetailResponse;
import com.fullstack.frontend.Retro.RatingRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;

public class BottomDetailFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_detail, container, false);
        super.onViewCreated(v, savedInstanceState);
        Bundle mArgs = getArguments();
        TextView orderID = (TextView) v.findViewById(R.id.OrderID_filling);
        TextView createdTime = (TextView) v.findViewById(R.id.create_time_filling);
        TextView status = (TextView) v.findViewById(R.id.status_filling);
        TextView category = (TextView) v.findViewById(R.id.category_filling);
        TextView payment  = (TextView) v.findViewById(R.id.payment);
        TextView from_address = (TextView) v.findViewById(R.id.from_address_filling);
        TextView to_address = (TextView) v.findViewById(R.id.to_address_filling);

        int ID = Integer.parseInt(mArgs.getString("OrderID"));

        TextView submit = v.findViewById(R.id.submit);
        RatingBar ratingBar = v.findViewById(R.id.rating);
        ratingBar.setMax(5);
        submit.setOnClickListener(v1 -> {
            RatingRequest request = new RatingRequest(ID, (int) ratingBar.getRating());
            goRating(request, ratingBar, submit);
        });

        orderID.setText(mArgs.getString("OrderID"));
        createdTime.setText(mArgs.getString("createdTime"));
        status.setText(mArgs.getString("status"));
        category.setText(mArgs.getString("category"));
        payment.setText(mArgs.getString("payment"));
        from_address.setText(mArgs.getString("from_address"));
        to_address.setText(mArgs.getString("to_address"));


        //call ratingBar API
        Call<BaseResponse<OrderDetailResponse>> orderDetailResponse = apiService.postOrderDetail(new OrderDetailRequest(ID));
        orderDetailResponse.enqueue(new Callback<BaseResponse<OrderDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<OrderDetailResponse>> call, retrofit2.Response<BaseResponse<OrderDetailResponse>> response) {
                if (response.isSuccessful()) {
                    OrderDetailResponse response1 = response.body().response;
                    ratingBar.setRating(response1.feedback);
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<com.fullstack.frontend.Retro.OrderDetailResponse>> call, Throwable t) {
                Log.d("test:::", "failed to load from server");
            }
        });







        submit.setOnClickListener(v1 -> {
            RatingRequest request = new RatingRequest(ID, (int) ratingBar.getRating());
            goRating(request, ratingBar, submit);
        });

        return v;


    }
    private final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private void goRating(RatingRequest request, RatingBar ratingBar, TextView submit) {
        Call<BaseResponse<String>> rating = apiService.goRating(request);
        rating.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, retrofit2.Response<BaseResponse<String>> response) {
                Toast.makeText(getContext(), "Submit Feedback Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

            }
        });
    }
}

