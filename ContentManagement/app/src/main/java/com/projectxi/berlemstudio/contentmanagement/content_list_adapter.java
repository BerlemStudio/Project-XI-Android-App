package com.projectxi.berlemstudio.contentmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by patawat on 2/1/2017 AD.
 */

public class content_list_adapter extends RecyclerView.Adapter<content_list_adapter.ViewHolder> {
    private ArrayList<story> mDataset;
    private ArrayList<story> selectedList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public android.widget.ImageView ImageView;
        public TextView name;
        public TextView des;
        public Button select;
        public Boolean selection;

        public ViewHolder(View v) {
            super(v);
            ImageView = (ImageView)v.findViewById(R.id.image);
            name = (TextView)v.findViewById(R.id.name);
            des = (TextView)v.findViewById(R.id.des);
            select = (Button)v.findViewById(R.id.select);
            this.selection = false;
        }
    }

    public content_list_adapter(ArrayList s) {
        mDataset = s;
        this.selectedList = new ArrayList<story>();
    }

    @Override
    public content_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        content_list_adapter.ViewHolder vh = new content_list_adapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final content_list_adapter.ViewHolder holder, final int position) {
        Context context = holder.ImageView.getContext();
        int id = context.getResources().getIdentifier(mDataset.get(position).getImg_path(),"drawable", context.getPackageName());
        holder.ImageView.setImageResource(id);
        holder.name.setText(mDataset.get(position).getName());
        holder.des.setText(mDataset.get(position).getDes());
        holder.select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!holder.selection){
                    holder.selection = true;
                    holder.select.setText("selected");
                    selectedList.add(mDataset.get(position));
                }else {
                    holder.selection = false;
                    holder.select.setText("Click");
                    selectedList.remove(mDataset.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

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

    public ArrayList getSelectedList(){
        return this.selectedList;
    }
}