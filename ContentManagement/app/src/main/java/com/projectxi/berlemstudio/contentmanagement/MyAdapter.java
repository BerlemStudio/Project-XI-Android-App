package com.projectxi.berlemstudio.contentmanagement;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

        public ViewHolder(View v) {
            super(v);
            ImageView = (ImageView)v.findViewById(R.id.image);
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
        Context context = holder.ImageView.getContext();
        int id = context.getResources().getIdentifier(mDataset.get(position).getImg_path(),"drawable", context.getPackageName());
        holder.ImageView.setImageResource(id);

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

    public ArrayList getList(){
        return this.mDataset;
    }

    public String getString(){
        return this.mDataset.get(1).getName();
    }
}
