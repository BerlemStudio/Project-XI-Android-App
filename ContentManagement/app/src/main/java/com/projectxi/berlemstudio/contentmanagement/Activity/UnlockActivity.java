package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.projectxi.berlemstudio.contentmanagement.Adapter.content_list_adapter;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import java.util.ArrayList;

public class UnlockActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private content_list_adapter mAdapter;
    private ArrayList<Scene> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("ปลดล็อค");

        ArrayList myDataset = null;
        try {
            myDataset = query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAdapter = new content_list_adapter( myDataset, this );
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList query(){
        ArrayList list = new ArrayList<Scene>();
        return list;
    }
}
