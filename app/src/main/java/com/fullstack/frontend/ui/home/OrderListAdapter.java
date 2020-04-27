package com.fullstack.frontend.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.LinearViewHolder> {
    private List<OrderResponse> orderResponses = new LinkedList<>();

    public OrderListAdapter() {
    }

    public void setOrders(List<OrderResponse> orders) {
        this.orderResponses.clear();;
        this.orderResponses.addAll(orders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.orderlist_item,parent,false));
        View itemView = View.inflate(parent.getContext(), R.layout.orderlist_item, null);
        return new LinearViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OrderResponse orderResponse = orderResponses.get(position);

        holder.status.setText(String.valueOf(orderResponse.status));
        // Bug
        holder.order_id.setText(String.valueOf(orderResponse.id));
        holder.category.setText(orderResponse.category);
        holder.receiver.setText(orderResponse.lastname);
    }

    @Override
    public int getItemCount() {

        return orderResponses.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView status, order_id, category, receiver;

        public LinearViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            order_id = itemView.findViewById(R.id.order_id);
            category = itemView.findViewById(R.id.category);
            receiver = itemView.findViewById(R.id.receiver);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void OnItemClick(View view);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

