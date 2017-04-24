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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.projectxi.berlemstudio.contentmanagement.Adapter.Ordering_adapter;
import com.projectxi.berlemstudio.contentmanagement.StartPlaying;
import com.projectxi.berlemstudio.contentmanagement.model.DbHelper;
import com.projectxi.berlemstudio.contentmanagement.ItemTouchHelperCallback;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.convertArrays;
import com.projectxi.berlemstudio.contentmanagement.dialog.startDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class OrderingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Ordering_adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private DbHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.verifyStoragePermissions();

//        set Recycle view
        setContentView(R.layout.activity_content_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        set tool bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

//        set action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.Ordering_activity_name);
//        ab.setTitle("ลำดับเนื้อเรื่อง");

        Intent intent = getIntent();
        ArrayList<Scene> myDataset;
        try {
            myDataset = (ArrayList<Scene>) intent.getSerializableExtra("selectedList");
            this.mAdapter = new Ordering_adapter( myDataset );
            ItemTouchHelper.Callback callback =
                    new ItemTouchHelperCallback(this.mAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
        }catch (Exception e){
            System.out.print(e.toString());
        }


    }

    // Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.orderingbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:{
                this.finish();
                return true;
            }
            case R.id.save:{
                Intent intent = new Intent(this, CreateStoryActivity.class);
                ArrayList<Scene> list = mAdapter.getList();
                String[] order = new String[list.size()];
                for (int i = 0; i < list.size() ; i++){
                    order[i] = list.get(i).getId();
                }
                convertArrays convertor = new convertArrays();
                String convert = convertor.convertArrayToString(order);
                intent.putExtra("selectedList", order);
                startActivity(intent);
                return true;
            }
            case R.id.start:{
                StartPlaying.start(mAdapter,this);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void saveLastStart(String story,String des,String creator, String[] order){
        myHelper = new DbHelper(this);
        convertArrays convertor = new convertArrays();
        String convert = convertor.convertArrayToString(order);
        myHelper.insertStory(story , des, creator, convert);
        Log.d("saveStory", "saveLastStart: True");
    }

    // Verify storage to write on
    public void verifyStoragePermissions(){
        int permisstion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permisstion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }

}
