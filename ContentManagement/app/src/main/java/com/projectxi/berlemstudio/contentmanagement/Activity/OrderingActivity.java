package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.Manifest;
import android.content.Intent;
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

import com.projectxi.berlemstudio.contentmanagement.Adapter.ordering_adapter;
import com.projectxi.berlemstudio.contentmanagement.CreateStoryActivity;
import com.projectxi.berlemstudio.contentmanagement.DbHelper;
import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.convertArrays;
import com.projectxi.berlemstudio.contentmanagement.dialog.startDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class OrderingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ordering_adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private DbHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        ab.setTitle("ลำดับเนื้อเรื่อง");

        Intent intent = getIntent();
        ArrayList<Scene> myDataset;
        try {
            myDataset = (ArrayList<Scene>) intent.getSerializableExtra("selectedList");
            this.mAdapter = new ordering_adapter( myDataset );
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
            case R.id.swap:{
                if (this.mAdapter.getItemCount() != 1)
                    this.mAdapter.onItemMove(0, 1);
                return true;
            }
            case android.R.id.home:{
                this.finish();
                return true;
            }
            case R.id.save:{
                Intent intent = new Intent(this, CreateStoryActivity.class);
                ArrayList<Scene> list = mAdapter.getList();
                String[] order = new String[list.size()];
                for (int i = 0; i < list.size() ; i++){
                    order[i] = list.get(i).getScene();
                }
                convertArrays convertor = new convertArrays();
                String convert = convertor.convertArrayToString(order);
                intent.putExtra("selectedList", convert);
                startActivity(intent);
                return true;
            }
            case R.id.start:{
                startDialog dialog = new startDialog();
                ArrayList<Scene> list = mAdapter.getList();

                String[] arrayOrder = new String[list.size()];
                for (int i = 0; i < list.size() ; i++){
                    arrayOrder[i] = "\""+list.get(i).getScene()+"\"";
                }
                String[] order = new String[list.size()];
                for (int i = 0; i < list.size() ; i++){
                    order[i] = list.get(i).getScene();
                }
                saveLastStart("lastStory", "", "Patawat", order);
                JSONObject orderArray = new JSONObject();
                String input = "{"+"\"orderArray\""+":"+Arrays.toString(arrayOrder)+"}";
                dialog.setDialog(input, this);
                dialog.show(getFragmentManager(),"StartWarning");
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void saveLastStart(String story,String des,String creator, String[] order){
        myHelper = new DbHelper(this);
        convertArrays convertor = new convertArrays();
        String convert = convertor.convertArrayToString(order);
//        myHelper.deleteAll();
        myHelper.insertStory("การเล่นครั้งล่าสุด","เนื้อหาที่ใช้เขาดูครั้งล่าสุด","Patawat",convert);
        Log.d("saveStory", "saveLastStart: ");
//        myHelper.insertStory("Saturn","TestDes","Patawat");
    }

    public void verifyStoragePermissions(){
        int permistion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permistion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }

}
