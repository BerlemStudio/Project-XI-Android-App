package com.projectxi.berlemstudio.contentmanagement.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.projectxi.berlemstudio.contentmanagement.Adapter.unlock_scene_list_adapter;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.config;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patawat on 4/23/2017 AD.
 */

public class unlockDialog extends DialogFragment {
    private String sceneID;
    private Context context;
    private ProgressDialog progress;
    private unlock_scene_list_adapter.ViewHolder holder;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.confirm_unlock, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        progressdialog();
                        unlock();
                    }
                })
                .setNegativeButton(R.string.cancle_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
    public void setSceneID(String sceneID){
        this.sceneID = sceneID;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void setHolder(unlock_scene_list_adapter.ViewHolder holder){
        this.holder = holder;
    }
    private void unlock(){
        String url = config.url+config.unlock;
//        String url = "http://ec2-54-169-97-8.ap-southeast-1.compute.amazonaws.com/api/me/unlock";
        RequestQueue queue = Volley.newRequestQueue(context);

        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);
        final String access_token = sharedPref.getString(getString(R.string.access_token),"");
        final String token_type = sharedPref.getString(getString(R.string.token_type),"");

        Map<String, String> params = new HashMap<>();
        params.put("scene_id", sceneID);

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ContentResponse", response.toString());
                holder.select.setImageResource(R.drawable.ic_lock_open_black_24dp);
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Log.d("ContentResponse", "onResponseERROR: "+error.toString());
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("scene_id", sceneID);
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
        this.progress = new ProgressDialog(this.context);
        progress.setTitle("Loading");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }
}
