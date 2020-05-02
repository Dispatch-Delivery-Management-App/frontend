package com.fullstack.frontend.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.OrderResponse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int text_item = 1;
    public static final int order_item = 2;
    public static int index = 0;
    private List<OrderResponse> orderResponses = new LinkedList<>();
    private HashMap<Integer, Object> itemHashmap= new HashMap<>();

    @Override
    public int getItemViewType(int position) {
        if(itemHashmap.get(position) instanceof String){
            return text_item;
        }
        return order_item;
    }


    public OrderListAdapter() {
    }

    public void setOrders(List<OrderResponse> orders) {
        this.orderResponses.clear();
        this.orderResponses.addAll(orders);

        int i = 0;
        int j = 0;
        while(j < orderResponses.size() && orderResponses.get((j)).status < 2){
            itemHashmap.put(i, orderResponses.get(j));
            i++;
            j++;
        }
        itemHashmap.put(i, "Shipping");
        i++;
        while(j < orderResponses.size() && orderResponses.get((j)).status < 4){
            itemHashmap.put(i, orderResponses.get(j));
            i++;
            j++;
        }
        itemHashmap.put(i, "Delivered");
        i++;
        while(j < orderResponses.size() && orderResponses.get((j)).status < 5){
            itemHashmap.put(i, orderResponses.get(j));
            i++;
            j++;
        }

        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.orderlist_item,parent,false));
        View itemView;
        StatusTextHolder textHolder;
        LinearViewHolder itemHolder;
        if(viewType == text_item){
            itemView = View.inflate(parent.getContext(), R.layout.status_text, null);
            textHolder = new StatusTextHolder(itemView);
            return textHolder;
        }else if(viewType == order_item){
            // itemView = View.inflate(parent.getContext(), R.layout.orderlist_item, null);
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_item, parent, false);
            itemHolder = new LinearViewHolder(itemView);
            return itemHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //  int type =  getItemViewType(position);
        OrderResponse orderResponse;
        if(holder instanceof StatusTextHolder) {
            String statusText = (String) itemHashmap.get(position);
            StatusTextHolder textHolder = (StatusTextHolder) holder;
            textHolder.status_text.setText(statusText);
            textHolder.itemView.setOnClickListener(null);
        }

        if(holder instanceof LinearViewHolder) {
            LinearViewHolder itemHolder = (LinearViewHolder) holder;
            String statusString = "undecided";
            orderResponse = (OrderResponse) itemHashmap.get(position);
            if (orderResponse.status == 1) {
                statusString = "draft";
            }
            if (orderResponse.status == 2) {
                statusString = "notstart";
            }
            if (orderResponse.status == 3) {
                statusString = "shipped";
            }
            if (orderResponse.status == 4) {
                statusString = "complete";
            }
            itemHolder.status.setText("Status: " + statusString);
            itemHolder.order_id.setText(String.valueOf("Order ID: " + orderResponse.id));
            itemHolder.category.setText("Category: " + orderResponse.category);
            itemHolder.receiver.setText("Receiver: " + orderResponse.lastname);
            itemHolder.itemView.setTag(position);


            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(orderResponse.id);
                    }
                }

            });

        }

    }




    @Override
    public int getItemCount() {
        return itemHashmap.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView status, order_id, category, receiver;

        LinearViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            order_id = itemView.findViewById(R.id.order_id);
            category = itemView.findViewById(R.id.category);
            receiver = itemView.findViewById(R.id.receiver);
        }
    }

    public static class StatusTextHolder extends RecyclerView.ViewHolder {

        public TextView status_text;

        StatusTextHolder(View itemView) {
            super(itemView);
            status_text = itemView.findViewById(R.id.status_view);
        }
    }

    public interface OnItemClickListener {
        public void OnItemClick(int id);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}