package com.fullstack.frontend.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.ApiClient;
import com.fullstack.frontend.Retro.ApiInterface;
import com.fullstack.frontend.Retro.BaseResponse;
import com.fullstack.frontend.Retro.OrderDetailRequest;
import com.fullstack.frontend.Retro.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.LinearViewHolder>{
    private Context mContext;

    public OrderListAdapter(Context context){
        this.mContext=context;
    }
    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.orderlist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        holder.status.setText(OrderListRepository.statuses.get(position));
        holder.order_id.setText(OrderListRepository.ids.get(position));
        holder.category.setText(OrderListRepository.categories.get(position));
        holder.receiver.setText(OrderListRepository.receivers.get(position));
    }

    @Override
    public int getItemCount() {
        return  OrderListRepository.statuses.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView status, order_id, category, receiver;

        public LinearViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            order_id = itemView.findViewById(R.id.order_id);
            category = itemView.findViewById(R.id.category);
            receiver = itemView.findViewById(R.id.receiver);
        }
    }
}
