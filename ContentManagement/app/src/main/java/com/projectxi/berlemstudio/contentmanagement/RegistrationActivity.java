package com.projectxi.berlemstudio.contentmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.projectxi.berlemstudio.contentmanagement.Activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repassword;
    private EditText email;


    private Button submite;
    private Button cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.repassword = (EditText) findViewById(R.id.confirmPassword);
        this.email = (EditText) findViewById(R.id.email);

        submite = (Button) findViewById(R.id.submit);
        cancle = (Button) findViewById(R.id.cancle);

        submite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                register();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
    public void register(){
        Map<String, String> params = new HashMap<>();
        params.put("name", this.username.getText().toString().trim().toLowerCase());
        params.put("password", this.password.getText().toString().trim());
        params.put("email", this.email.getText().toString().trim());

        final Map<String, String> test = params;
        Log.d("test", "onCreate: "+params.toString());
        final Context context = this;
        RequestQueue queue = Volley.newRequestQueue(context);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, config.registration_url,new JSONObject(test),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("change Pass response -->> " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(request);

    }
}
