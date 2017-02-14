package com.projectxi.berlemstudio.contentmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projectxi.berlemstudio.contentmanagement.Activity.StorySceneListActivity;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Story;

/**
 * Created by patawat on 2/13/2017 AD.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private Story[] dataset;
    private Context context;

    public StoryAdapter(Story[] dataset, Context context){
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_card, parent, false);

        StoryAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, final int position){
        holder.mTextView.setText(dataset[position].getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StorySceneListActivity.class);
                String[] scene = dataset[position].getScene();
                intent.putExtra("scene", scene);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return this.dataset.length;
    }
    // create viewHolder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name_story_card);
        }
    }

}
