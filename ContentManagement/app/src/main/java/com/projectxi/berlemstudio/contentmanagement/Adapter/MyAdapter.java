package com.projectxi.berlemstudio.contentmanagement.Adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.projectxi.berlemstudio.contentmanagement.Activity.SceneInfoActivity;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by patawat on 1/11/2017 AD.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>implements StartingAdapter {
    private ArrayList<Scene> mDataset;
    final Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ImageView;
        public TextView name;
        public TextView des;
        public TextView tag;
        public View view;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            ImageView = (ImageView)v.findViewById(R.id.image);
            name = (TextView)v.findViewById(R.id.name);
//            des = (TextView)v.findViewById(R.id.des);
            tag = (TextView) v.findViewById(R.id.tag);
        }
    }

    public MyAdapter(ArrayList s, Context context) {
        mDataset = s;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_content_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        final Context context = this.context;
        int id = context.getResources().getIdentifier(mDataset.get(position).getImg_path(),"drawable", context.getPackageName());
        holder.ImageView.setImageResource(id);
        holder.name.setText(mDataset.get(position).getName());

        holder.view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(context, SceneInfoActivity.class);
                intent.putExtra("sceneData", mDataset.get(position));
                context.startActivity(intent);
            }
        });
//  holder.des.setText(mDataset.get(position).getDes());
        holder.tag.setText(mDataset.get(position).getTag());
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
}
