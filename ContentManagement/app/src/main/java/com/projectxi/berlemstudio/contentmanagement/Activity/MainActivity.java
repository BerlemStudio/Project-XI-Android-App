package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projectxi.berlemstudio.contentmanagement.R;

public class MainActivity extends ActionBarActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // set screen to portrait
        String[] mPlanetTitles = {"Android", "iOS", "Windows", "OS X", "Linux" };
//        mPlanetTitles = this.mPlanetTitles;
//        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.navList);
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, mPlanetTitles));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
    }

    public void onClickQuickStart(View view){
        Intent intent = new Intent(this, StoryListActivity.class);
        startActivity(intent);
    }

    public void onClickNormalStart(View view){
        Intent intent = new Intent(this, ContentList.class);
        startActivity(intent);
    }

    public void onClickUnlock(View view){
        Intent intent = new Intent(this, UnlockActivity.class);
        startActivity(intent);
    }
}
