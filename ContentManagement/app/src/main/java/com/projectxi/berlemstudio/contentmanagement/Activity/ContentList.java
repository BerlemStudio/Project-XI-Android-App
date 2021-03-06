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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;
import com.projectxi.berlemstudio.contentmanagement.Adapter.content_list_adapter;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.config;
import com.projectxi.berlemstudio.contentmanagement.dialog.nextDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ContentList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView testText;
    private content_list_adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private ArrayList<Scene> list;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressdialog();
        setContentView(R.layout.activity_content_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.content_activity_name);

        ArrayList myDataset = null;
        try {
            myDataset = query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAdapter = new content_list_adapter( myDataset, this );
        mRecyclerView.setAdapter(mAdapter);
    }

    // Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contentbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:{
                this.finish();
                return true;
            }
            case R.id.next:{
                if(mAdapter.getSelectedList().size()==0){
                    nextDialog dialog = new nextDialog();
                    dialog.show(getFragmentManager(), "WarningSelected");
                }else{
                    Intent intent = new Intent(this, OrderingActivity.class);
                    intent.putExtra("selectedList", mAdapter.getSelectedList());
                    startActivity(intent);
                }
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

    public void verifyStoragePermissions(){
        int permistion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permistion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }

    public ArrayList query(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = config.url+config.user_scene_list_unlock;
        this.list = new ArrayList<Scene>();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);
        final String access_token = sharedPref.getString(getString(R.string.access_token),"");
        final String token_type = sharedPref.getString(getString(R.string.token_type),"");

        Log.d("auth", "getHeaders: "+ token_type+" "+access_token);
        // Request a string response from the provided URL.

        final JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ContentResponse", response.toString());
                for(int i=0; i<response.length() ;i++){
                    JSONObject objResponse = null;
                    try {
                        objResponse = response.getJSONObject(i);
                        JSONObject obj = objResponse.getJSONObject("scene");
                        String id = obj.getString("id");
                        String name = obj.getString("name");
                        String des = obj.getString("descrisption");
                        String Img_path = obj.getString("image_path");
                        String scene = obj.getString("scene_name");
                        String tag = obj.getString("tag");
                        String eng = obj.getString("english");
                        Scene test= new Scene(id, name, des, Img_path, scene, tag, eng);
                        list.add(test);
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progress.dismiss();
                Log.d("list", "onResponse: "+list.toString());
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
        return list;
    }
    private void progressdialog(){
        this.progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }
}
