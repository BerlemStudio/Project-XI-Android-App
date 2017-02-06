package com.projectxi.berlemstudio.contentmanagement;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class OrderingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ordering_adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

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
        ArrayList<story> myDataset;
        try {
            myDataset = (ArrayList<story>) intent.getSerializableExtra("selectedList");
            this.mAdapter = new ordering_adapter( myDataset );
            mRecyclerView.setAdapter(mAdapter);
        }catch (Exception e){
            System.out.print(e.toString());
        }


    }

    // Create Menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.orderingbar, menu);
        return true;
    }

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
            case R.id.start:{
                startDialog dialog = new startDialog();
                ArrayList<story> list = mAdapter.getList();

                String[] arrayOrder = new String[list.size()];
                for (int i = 0; i < list.size() ; i++){
                    arrayOrder[i] = "\""+list.get(i).getScene()+"\"";
                }
//                try {
                    JSONObject orderArray = new JSONObject();
                    String input = "{"+"\"orderArray\""+":"+Arrays.toString(arrayOrder)+"}";
//                    orderArray.put(input, arrayOrder);

                    dialog.setDialog(input, this);
                    dialog.show(getFragmentManager(),"StartWarning");
                    return true;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    return false;
//                }
            }
            default: return super.onOptionsItemSelected(item);
        }
    }


    public void verifyStoragePermissions(){
        int permistion = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permistion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }
}
