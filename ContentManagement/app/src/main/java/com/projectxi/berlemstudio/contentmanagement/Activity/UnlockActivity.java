package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.projectxi.berlemstudio.contentmanagement.Adapter.unlock_scene_list_adapter;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.dialog.nextDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;
import com.projectxi.berlemstudio.contentmanagement.res.Scene_unlock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnlockActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private unlock_scene_list_adapter mAdapter;
    private ArrayList<Scene_unlock> list;
    private ProgressDialog progress;
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

        progressdialog();

        ArrayList myDataset = null;
        try {
            myDataset = query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAdapter = new unlock_scene_list_adapter( myDataset, this );
        mRecyclerView.setAdapter(mAdapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home: {
                this.finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList query(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-54-169-97-8.ap-southeast-1.compute.amazonaws.com/api/me/user_scene_list";
        this.list = new ArrayList<Scene_unlock>();
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
                        int unlock_int = objResponse.getInt("unlock");
                        Boolean unlock;
                        if (unlock_int==0){
                            Log.d("unlock", "onResponse: false");
                            unlock = false;
                        }else{
                            Log.d("unlock", "onResponse: True");
                            unlock = true;
                        }
                        String tag = "test";
                        Scene_unlock scene_unlock = new Scene_unlock(id, name, des, Img_path, scene, tag, unlock);
                        list.add(scene_unlock);
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
