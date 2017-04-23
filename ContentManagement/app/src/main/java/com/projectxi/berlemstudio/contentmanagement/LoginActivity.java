package com.projectxi.berlemstudio.contentmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button registration;
    private Context mContext;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        registration = (Button) findViewById(R.id.registration);

        mContext = this;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog();
                checklogin();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checklogin() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String username = this.username.getText().toString().trim().toLowerCase();
        String password = this.password.getText().toString().trim();

        String url = "http://ec2-54-169-97-8.ap-southeast-1.compute.amazonaws.com/oauth/token";
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("client_id", config.client_id);
        params.put("client_secret", config.client_secret);
        params.put("grant_type", config.grant_type);

        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String access_token = response.getString("access_token");
                            String token_type = response.getString("token_type");

                            Log.d("login", "onResponse: "+token_type+access_token);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.access_token), access_token);
                            editor.commit();
                            editor.putString(getString(R.string.token_type), token_type);
                            editor.commit();
                            progress.dismiss();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(mContext, error.toString(), duration);
                        toast.show();
                        System.out.println("change Pass response -->> " + error.toString());
                    }
                }
        );
        queue.add(request);
    }

    private void progressDialog(){
        this.progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }


}
