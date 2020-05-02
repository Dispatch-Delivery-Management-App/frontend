package com.fullstack.frontend.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fullstack.frontend.R;
import com.fullstack.frontend.Retro.SearchResponse;

import java.util.LinkedList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.LinearViewHolder> {

    private List<SearchResponse> searchResponses = new LinkedList<>();

    public SearchAdapter() {
    }

    public void setSearch (List<SearchResponse> searches) {
        this.searchResponses.clear();;
        this.searchResponses.addAll(searches);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchView = LayoutInflater.from(parent.getContext()).inflate( R.layout.search_adapter_list, parent, false);
        return new LinearViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.LinearViewHolder holder, int position) {
        SearchResponse searchResponse = searchResponses.get(position);

        holder.id.setText(String.valueOf("ID: " + searchResponse.id));
        holder.status.setText(String.valueOf("Status: " + searchResponse.status));
        holder.category.setText(String.valueOf("Category: " + searchResponse.category));
        holder.lastname.setText(String.valueOf("Receiver: " + searchResponse.lastname));
//        holder.item_info.setText(String.valueOf("Item Info: " + searchResponse.item_info));
//        holder.shipping_method.setText(String.valueOf("Shipping Method: "+ searchResponse.shipping_method));
//        holder.total_cost.setText(String.valueOf("Total Cost: " + searchResponse.total_cost));
//        holder.tracking_id.setText(String.valueOf("Tracking Id: " + searchResponse.tracking_id));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchItemListener.onItemClick(searchResponse.id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchResponses.size();
    }


    public class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView  id,status, category, lastname;
        OnSearchItemListener onSearchItemClickListener;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            status = itemView.findViewById(R.id.status);
            category = itemView.findViewById(R.id.category);
            lastname = itemView.findViewById(R.id.lastname);
//            item_info = itemView.findViewById(R.id.item_info);
//            shipping_method = itemView.findViewById(R.id.shipping_method);
//            total_cost = itemView.findViewById(R.id.total_cost);
//            tracking_id = itemView.findViewById(R.id.tracking_id);

//            itemView.setOnClickListener( v -> {
//                onSearchItemListener.onItemClick(getAdapterPosition(), SearchAdapter.this, itemView);
//            });
//            this.onSearchItemClickListener = onSearchItemListener;
//            itemView.setOnClickListener(this);


        }
    }

    public interface OnSearchItemListener {
        void onItemClick(int id);
//        void onItemClick(int position, SearchAdapter adapter, View itemView);
    }

    private OnSearchItemListener onSearchItemListener;

    public void setOnSearchItemListener(OnSearchItemListener onSearchItemListener) {
        this.onSearchItemListener = onSearchItemListener;
    }
}
