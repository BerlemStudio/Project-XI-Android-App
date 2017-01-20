package com.projectxi.berlemstudio.contentmanagement;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.projectxi.berlemstudio.contentmanagement.story;

public class ContentList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ArrayList myDataset = null;
        try {
            myDataset = getJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.mAdapter = new MyAdapter( myDataset );
        mRecyclerView.setAdapter(mAdapter);
    }

    // Create Menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contentbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.swap:{
                this.mAdapter.onItemMove(0, 1);
//                onBackPressed();
                return true;
            }
            case R.id.start:{
                System.out.print("start");
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    public String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("content.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList getJSON() throws JSONException {
        JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
        JSONArray array = jsonObj.getJSONArray("content");
        ArrayList<story> list = new ArrayList<>();

        for (int count = 0 ; count < array.length() ; count++){
            JSONObject obj = array.getJSONObject(count);
            String name = obj.getString("name");
            String des = obj.getString("des");
            String Img_path = obj.getString("img_path");
            story test= new story(name, des, Img_path);
            list.add(test);
        }

        return list;
    }

    public void createActionListener(){

    }
}
