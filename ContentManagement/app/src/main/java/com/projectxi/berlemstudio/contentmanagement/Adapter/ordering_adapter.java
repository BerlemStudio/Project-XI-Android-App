package com.projectxi.berlemstudio.contentmanagement.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by patawat on 2/4/2017 AD.
 */

public class ordering_adapter extends RecyclerView.Adapter<ordering_adapter.ViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Scene> orderData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name_ordering_card);
        }
    }

    @Override
    public void onItemDismiss(int position){
        this.orderData.remove(position);
        notifyItemRemoved(position);
    }

    public ordering_adapter(ArrayList<Scene> data){
        this.orderData =  data;
    }
    @Override
    public ordering_adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ordering_card, parent, false);

        ordering_adapter.ViewHolder vh = new ordering_adapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ordering_adapter.ViewHolder holder, final int position) {

        holder.name.setText(this.orderData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.orderData.size();
    }

    public ArrayList getList(){
        return this.orderData;
    }


    @Override
    public void onItemMove(int FromPosition, int ToPosition){
        Collections.swap(this.orderData, FromPosition, ToPosition);
        notifyItemMoved(FromPosition, ToPosition);
    }

}
