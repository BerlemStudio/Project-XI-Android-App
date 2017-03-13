package com.projectxi.berlemstudio.contentmanagement.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectxi.berlemstudio.contentmanagement.R;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

public class SceneInfoActivity extends AppCompatActivity {

    private Scene scene;
    private ImageView imageView;
    private TextView name;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_info);
        Intent intent = getIntent();

        scene = (Scene) intent.getSerializableExtra("sceneData");


        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.name = (TextView) findViewById(R.id.name);
        this.description = (TextView) findViewById(R.id.des);

        int id = getResources().getIdentifier(scene.getImg_path(),"drawable", getPackageName());
        imageView.setImageResource(id);

        name.setText(scene.getName());
        description.setText(scene.getDes());


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //        set action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(scene.getName());
    }


}
