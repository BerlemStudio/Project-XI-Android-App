package com.projectxi.berlemstudio.contentmanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projectxi.berlemstudio.contentmanagement.Activity.SceneInfoActivity;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.dialog.nextDialog;
import com.projectxi.berlemstudio.contentmanagement.dialog.unlockDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;
import com.projectxi.berlemstudio.contentmanagement.res.Scene_unlock;

import java.util.ArrayList;

/**
 * Created by patawat on 4/23/2017 AD.
 */

public class unlock_scene_list_adapter extends RecyclerView.Adapter<unlock_scene_list_adapter.ViewHolder> {
    private ArrayList<Scene_unlock> mDataset;
    private ArrayList<Scene_unlock> selectedList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public android.widget.ImageView ImageView;
        public TextView name;
        public TextView des;
        public ImageButton select;
        public TextView tag;
        public Boolean selection;
        public RelativeLayout card;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            ImageView = (android.widget.ImageView)v.findViewById(R.id.image);
            name = (TextView)v.findViewById(R.id.name);
            des = (TextView)v.findViewById(R.id.des);
            select = (ImageButton)v.findViewById(R.id.select);
            tag = (TextView) v.findViewById(R.id.tag);
            this.selection = false;
        }
    }

    public unlock_scene_list_adapter(ArrayList s, Context context) {
        mDataset = s;
        this.context = context;
        this.selectedList = new ArrayList<Scene_unlock>();
    }

    @Override
    public unlock_scene_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unlock_scene_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        unlock_scene_list_adapter.ViewHolder vh = new unlock_scene_list_adapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final unlock_scene_list_adapter.ViewHolder holder, final int position) {
        final Context Imagecontext = holder.ImageView.getContext();
        int id = Imagecontext.getResources().getIdentifier(mDataset.get(position).getImg_path(),"drawable", context.getPackageName());
        holder.view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(context, SceneInfoActivity.class);
                intent.putExtra("sceneData", mDataset.get(position));
                context.startActivity(intent);
            }
        });
        holder.tag.setText(mDataset.get(position).getTag());
        holder.ImageView.setImageResource(id);
        holder.name.setText(mDataset.get(position).getName());
        if (!mDataset.get(position).getUnlock()){
            holder.select.setImageResource(R.drawable.ic_lock_black_24dp);
        }
        else{
            holder.select.setImageResource(R.drawable.ic_lock_open_black_24dp);
        }

        holder.select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!mDataset.get(position).getUnlock()){
                    unlockDialog dialog = new unlockDialog();
                    android.app.FragmentManager manager = ((Activity) context).getFragmentManager();
                    dialog.setSceneID(mDataset.get(position).getId());
                    dialog.setContext(context);
                    dialog.setHolder(holder);
                    dialog.show(manager, "unlock warning");
                }else {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public String getString(){
        return this.mDataset.get(1).getName();
    }

    public ArrayList getSelectedList(){
        return this.selectedList;
    }
}
