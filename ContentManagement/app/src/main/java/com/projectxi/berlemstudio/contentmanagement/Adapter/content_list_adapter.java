package com.projectxi.berlemstudio.contentmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectxi.berlemstudio.contentmanagement.Activity.SceneInfoActivity;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import java.util.ArrayList;

/**
 * Created by patawat on 2/1/2017 AD.
 */

public class content_list_adapter extends RecyclerView.Adapter<content_list_adapter.ViewHolder> {
    private ArrayList<Scene> mDataset;
    private ArrayList<Scene> selectedList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public android.widget.ImageView ImageView;
        public TextView name;
        public TextView des;
        public ImageButton select;
        public Button detailButton;
        public Boolean selection;

        public ViewHolder(View v) {
            super(v);
            ImageView = (ImageView)v.findViewById(R.id.image);
            name = (TextView)v.findViewById(R.id.name);
            des = (TextView)v.findViewById(R.id.des);
            select = (ImageButton)v.findViewById(R.id.select);
            detailButton = (Button)v.findViewById(R.id.detailbutton);
            this.selection = false;
        }
    }

    public content_list_adapter(ArrayList s, Context context) {
        mDataset = s;
        this.context = context;
        this.selectedList = new ArrayList<Scene>();
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
        final Context Imagecontext = holder.ImageView.getContext();
        int id = Imagecontext.getResources().getIdentifier(mDataset.get(position).getImg_path(),"drawable", context.getPackageName());
        holder.ImageView.setImageResource(id);
        holder.name.setText(mDataset.get(position).getName());
        holder.des.setText(mDataset.get(position).getDes());
        holder.detailButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(context, SceneInfoActivity.class);
                context.startActivity(intent);
            }
        });
        holder.select.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
        holder.select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!holder.selection){
                    holder.selection = true;
                    String button = context.getString(R.string.button_selected);
                    holder.select.setImageResource(R.drawable.ic_check_box_black_24dp);
                    selectedList.add(mDataset.get(position));
                }else {
                    holder.selection = false;
                    String button = context.getString(R.string.button_select);
                    holder.select.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    selectedList.remove(mDataset.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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
