package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.projectxi.berlemstudio.contentmanagement.Adapter.StoryAdapter;
import com.projectxi.berlemstudio.contentmanagement.config;
import com.projectxi.berlemstudio.contentmanagement.model.DbHelper;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;
import com.projectxi.berlemstudio.contentmanagement.res.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.R.id.list;
public class StoryListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;
    private ArrayList<Story> stories;
    private DbHelper myHelper;
    private Context context;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        this.context = getApplicationContext();

        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        myHelper = new DbHelper(this);
//        stories = myHelper.getStoryList();
        progressdialog();
        // set Recycle view
        setContentView(R.layout.activity_content_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        getStory();


        // set tool bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.quick_start_name);
    }
    private void setAdapter(){
        Story[] arr = new Story[this.stories.size()];
        arr = this.stories.toArray(arr);

        this.mAdapter = new StoryAdapter( arr, this.context );
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    protected void onResume(){
        getStory();
//        Story[] arr = new Story[this.stories.size()];
//        arr = this.stories.toArray(arr);
//        this.mAdapter = new StoryAdapter( arr, this.context );
//        mRecyclerView.setAdapter(mAdapter);

        super.onResume();
    }
    // Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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

    private void getStory(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = config.url+config.get_story;
        this.stories = new ArrayList<Story>();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);
        final String access_token = sharedPref.getString(getString(R.string.access_token),"");
        final String token_type = sharedPref.getString(getString(R.string.token_type),"");

        final JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ContentResponse", response.toString());
                for(int i=0; i<response.length() ;i++){
                    JSONObject objResponse = null;
                    try {
                        objResponse = response.getJSONObject(i);
                        JSONArray scene = objResponse.getJSONArray("scene");
                        String id = objResponse.getString("id");
                        String name = objResponse.getString("name");
                        String des = objResponse.getString("description");

                        String[] list = new String[scene.length()];
                        for(int j = 0; j < scene.length(); j++){
                            String scene_id = scene.getJSONObject(j).getString("id");
                            list[j]=scene_id;
                        }
                        Story story = new Story(id, name, des, "", list);
                        stories.add(story);
                        Log.d("story", "onResponse: "+stories.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setAdapter();
                mAdapter.notifyDataSetChanged();
                progress.dismiss();
                Log.d("list", "onResponse: "+stories.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Log.d("ContentResponse", "onResponseERROR: "+error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  params = new HashMap<>();
                params.put("Authorization", token_type+" "+access_token);
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };
        queue.add(stringRequest);
    }
    private void progressdialog(){
        this.progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

}
