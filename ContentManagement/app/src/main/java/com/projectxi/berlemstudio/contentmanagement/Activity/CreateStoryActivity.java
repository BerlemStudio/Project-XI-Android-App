package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.config;
import com.projectxi.berlemstudio.contentmanagement.model.DbHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateStoryActivity extends AppCompatActivity {

    private EditText et_name, et_des;
    private String name, description, creator;
    Button saveButton;
    private DbHelper myHelper;
    private String[] scene;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);
        Intent intent = getIntent();
        this.scene = intent.getStringArrayExtra("selectedList");
        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("สร้างเนื้อเรื่องใหม่ ");

        myHelper = new DbHelper(this);
        et_name = (EditText) findViewById(R.id.input_story_name);
        et_des = (EditText) findViewById(R.id.input_story_des);
        saveButton = (Button) findViewById(R.id.button_send);
        this.saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View view){
                    progressdialog();
                    saveStory();
                }
            }
        );
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

    private void saveStory(){
        final Context context = this;
        String url = config.url+config.create_story;
        RequestQueue queue = Volley.newRequestQueue(context);

        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);
        final String access_token = sharedPref.getString(getString(R.string.access_token),"");
        final String token_type = sharedPref.getString(getString(R.string.token_type),"");

        Map params = new HashMap<>();
        params.put("name", et_name.getText().toString());
        params.put("description", et_des.getText().toString());
        params.put("scene_list", this.scene);

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ContentResponse", response.toString());
                progress.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, error.toString(), duration);
                toast.show();
                Log.d("ContentResponse", "onResponseERROR: "+error.toString());
            }
        }){
            protected Map<String, String> getParams()
            {
                Map  params = new HashMap<String, String>();
                params.put("name", et_name.getText().toString());
                params.put("description", et_des.getText().toString());
                params.put("scene_list", scene);
                return params;
            }
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
        this.progress.setTitle("Loading");
        this.progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        this.progress.show();
    }
}
