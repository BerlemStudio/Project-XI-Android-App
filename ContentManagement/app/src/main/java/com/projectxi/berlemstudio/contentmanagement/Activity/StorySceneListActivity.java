package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.projectxi.berlemstudio.contentmanagement.Adapter.MyAdapter;
import com.projectxi.berlemstudio.contentmanagement.Adapter.StartingAdapter;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.StartPlaying;
import com.projectxi.berlemstudio.contentmanagement.config;
import com.projectxi.berlemstudio.contentmanagement.model.DbHelper;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;
import com.projectxi.berlemstudio.contentmanagement.res.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StorySceneListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] sceneList;
    private String id;
    private DbHelper myHelper;
    private ProgressDialog progress;
    private ArrayList<Scene> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        sceneList = intent.getStringArrayExtra("scene");
        id = intent.getStringExtra("id");
        progressdialog();
        // Call objecr DB
        myHelper = new DbHelper(this);

        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.verifyStoragePermissions();

        setContentView(R.layout.activity_content_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayList myDataset = null;
        try {
//            myDataset = getJSON(sceneList);
            myDataset = getList(sceneList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.mAdapter = new MyAdapter( myDataset, this );
        mRecyclerView.setAdapter(mAdapter);

        this.createToolbar();
    }
    public void createToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.content_activity_name);
    }
    // Create Menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.story_content_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:{
                this.finish();
                return true;
            }
            case R.id.deleteStory:{
                progressdialog();
                this.deleteStory(id);
//                this.myHelper.delete(id);
                this.finish();
                return true;
            }
            case R.id.start:{
                StartPlaying.start(mAdapter,this);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    public String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("sceneInfo.json");
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

    public ArrayList getJSON(String[] sceneList) throws JSONException {
        JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
        ArrayList<Scene> list = new ArrayList<>();

        for (int count = 0; count<sceneList.length; count++){

            JSONObject obj = jsonObj.getJSONObject(sceneList[count]);
            String name = obj.getString("name");
            String des = obj.getString("des");
            String Img_path = obj.getString("img_path");
            String scene = obj.getString("scene");
            String tag = obj.getString("tag");
            Scene test= new Scene(name, des, Img_path, scene, tag);
            list.add(test);
        }

        return list;
    }

    public void verifyStoragePermissions(){
        int permistion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permistion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }

    private ArrayList getList(String[] sceneList){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        this.list = new ArrayList<>();

        for (int index = 0 ;index<sceneList.length;index++){
            String url = config.url+"/api/scene/"+sceneList[index];
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
                            String id = objResponse.getString("id");
                            String name = objResponse.getString("name");
                            String des = objResponse.getString("descrisption");
                            String Img_path = objResponse.getString("image_path");
                            String scene = objResponse.getString("scene_name");
                            String tag = objResponse.getString("tag");
                            Scene newscene= new Scene(id, name, des, Img_path, scene, tag);
                            list.add(newscene);
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    progress.dismiss();
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
        return list;

    }
    private void deleteStory(String id){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = config.url+"/api/story/"+id;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);
        final String access_token = sharedPref.getString(getString(R.string.access_token),"");
        final String token_type = sharedPref.getString(getString(R.string.token_type),"");
        final JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ContentResponse", response.toString());
                progress.dismiss();
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

    protected void onDestroy(){
        super.onDestroy();

    }

}
