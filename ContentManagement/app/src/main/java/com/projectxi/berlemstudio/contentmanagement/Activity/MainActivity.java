package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import com.projectxi.berlemstudio.contentmanagement.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // set screen to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
    }

    public void onClickQuickStart(View view){
//        Intent intent = new Intent(this, activity_story_scene_list.class);
//        startActivity(intent);
        Intent intent = new Intent(this, StoryListActivity.class);
        startActivity(intent);
    }

    public void onClickNormalStart(View view){
        Intent intent = new Intent(this, ContentList.class);
        startActivity(intent);
    }
}
