package com.fullstack.frontend.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.OrderResponse;

import java.util.LinkedList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int text_item = 1;
    public static final int order_item = 2;
    public static int index = 0;
    private List<OrderResponse> orderResponses = new LinkedList<>();
    private List<Integer> itemViewType = new LinkedList<>();

    @Override
    public int getItemViewType(int position) {
        return itemViewType.get(position);
    }

    public OrderListAdapter() {
    }

    public void setOrders(List<OrderResponse> orders) {
        this.orderResponses.clear();;
        this.orderResponses.addAll(orders);
        itemViewType.add(text_item);
        for(int i = 0; i < orders.size()-1; i++) {
            if(orders.get(i).status != orders.get(i+1).status) {
                itemViewType.add(order_item);
                itemViewType.add(text_item);
            }else {
                itemViewType.add(order_item);
            }
        }
        itemViewType.add(order_item);
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
            itemView = View.inflate(parent.getContext(), R.layout.orderlist_item, null);
            itemHolder = new LinearViewHolder(itemView);
            return itemHolder;
        }
        //return null;
//        return holder;
//        View itemView = View.inflate(parent.getContext(), R.layout.orderlist_item, null);
//        return new LinearViewHolder(itemView);
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type =  getItemViewType(position);
        OrderResponse orderResponse;
        if(type == text_item) {
            if (index == 0) {
                StatusTextHolder textHolder = (StatusTextHolder) holder;
                textHolder.status_text.setText("draft");
                index = 1;
            } else if (index == 1) {
                StatusTextHolder textHolder = (StatusTextHolder) holder;
                textHolder.status_text.setText("notstart");
                index = 2;
            } else if (index == 2) {
                StatusTextHolder textHolder = (StatusTextHolder) holder;
                textHolder.status_text.setText("shipped");
                index = 3;
            } else if (index == 3) {
                StatusTextHolder textHolder = (StatusTextHolder) holder;
                textHolder.status_text.setText("complete");
                index = 4;
            }
        }

        if(type == order_item) {
            LinearViewHolder itemHolder = (LinearViewHolder) holder;
            String statusString = "undecided";
            orderResponse = orderResponses.get(position - index);
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
            itemHolder.status.setText(statusString);
            // Bug
            itemHolder.order_id.setText(String.valueOf(orderResponse.id));
            itemHolder.category.setText(orderResponse.category);
            itemHolder.receiver.setText(orderResponse.lastname);
        }

    }




    @Override
    public int getItemCount() {
        return itemViewType.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView status, order_id, category, receiver;

        LinearViewHolder(View itemView) {
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

    public static class StatusTextHolder extends RecyclerView.ViewHolder {

        public TextView status_text;

        StatusTextHolder(View itemView) {
            super(itemView);
            status_text = itemView.findViewById(R.id.status_view);
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

