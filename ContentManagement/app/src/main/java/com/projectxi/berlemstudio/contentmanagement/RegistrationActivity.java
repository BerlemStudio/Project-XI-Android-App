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
    private ProgressDialog progress;
    private Button submite;
    private Button cancle;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.mContext = getApplicationContext();
        this.username = (EditText) findViewById(R.id.name);
        this.password = (EditText) findViewById(R.id.password);
        this.repassword = (EditText) findViewById(R.id.confirmPassword);
        this.email = (EditText) findViewById(R.id.email);

        submite = (Button) findViewById(R.id.submit);
        cancle = (Button) findViewById(R.id.cancle);

        submite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                progressDialog();
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

        if(!this.password.getText().toString().trim().equals(this.repassword.getText().toString().trim())){
            setToast("Password is not matched");
            progress.dismiss();
            return;
        }
        else{
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
                            progress.dismiss();
                            setToast("Register Success");
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();

                            setToast("Register Fail");
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

    private void progressDialog(){
        this.progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    private void setToast(String s){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(mContext, s, duration);
        toast.show();
    }
}
