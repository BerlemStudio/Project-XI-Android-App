package com.projectxi.berlemstudio.contentmanagement;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by patawat on 1/11/2017 AD.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Adapter {
//    private String[] mDataset;
    private ArrayList<story> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ImageView;
        public TextView name;
        public TextView des;
        public TextView img_path;

        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            des = (TextView)v.findViewById(R.id.des);
        }
    }

    public MyAdapter(ArrayList s) {
        mDataset = s;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.des.setText(mDataset.get(position).getDes());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onItemMove(int FromPosition, int ToPosition){
        Collections.swap(mDataset, FromPosition, ToPosition);
        notifyItemMoved(FromPosition, ToPosition);
    }
}
