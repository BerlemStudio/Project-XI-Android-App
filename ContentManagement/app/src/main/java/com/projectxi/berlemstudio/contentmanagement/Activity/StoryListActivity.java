package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.projectxi.berlemstudio.contentmanagement.Adapter.StoryAdapter;
import com.projectxi.berlemstudio.contentmanagement.DbHelper;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import android.R.id.list;
public class StoryListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;
    private List<String> stories;
    private DbHelper myHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        this.context = getApplicationContext();

        myHelper = new DbHelper(this);
        stories = myHelper.getStoryNameList();

        // set Recycle view
        setContentView(R.layout.activity_content_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] stockArr = new String[stories.size()];
        this.mAdapter = new StoryAdapter( stories.toArray(stockArr), this.context );
        mRecyclerView.setAdapter(mAdapter);

        //        set tool bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("เลือกเรื่องที่ต้องการ");
    }

    // Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d("Toolbar in storylist","test");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.story_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:{
                this.finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

}
