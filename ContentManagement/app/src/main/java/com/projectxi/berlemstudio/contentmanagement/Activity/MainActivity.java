package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.getBackground().setAlpha(0);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setTitle("Welcome :");

    }
    // Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case R.id.logout:{
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_login), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();
                sharedPref = getSharedPreferences(getString(R.string.last_save), Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.clear();
                editor.commit();
                this.finish();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
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
