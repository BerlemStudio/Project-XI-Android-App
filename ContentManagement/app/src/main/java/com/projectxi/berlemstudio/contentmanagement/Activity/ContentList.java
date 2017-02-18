package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.Manifest;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.projectxi.berlemstudio.contentmanagement.Adapter.content_list_adapter;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.dialog.nextDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ContentList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private content_list_adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.verifyStoragePermissions();

        setContentView(R.layout.activity_content_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("เนื้อเรื่อง");

        ArrayList myDataset = null;
        try {
            myDataset = getJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.mAdapter = new content_list_adapter( myDataset );
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
        ArrayList<Scene> list = new ArrayList<>();

        for (int count = 0 ; count < array.length() ; count++){
            JSONObject obj = array.getJSONObject(count);
            String name = obj.getString("name");
            String des = obj.getString("des");
            String Img_path = obj.getString("img_path");
            String scene = obj.getString("scene");

            Scene test= new Scene(name, des, Img_path, scene);
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

}
