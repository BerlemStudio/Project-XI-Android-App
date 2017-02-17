package com.projectxi.berlemstudio.contentmanagement;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateStoryActivity extends AppCompatActivity {

    private EditText et_name, et_des, et_creator;
    private String name, description, creator;
    Button saveButton;
    private DbHelper myHelper;
    private String scene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);
        Intent intent = getIntent();
        this.scene = intent.getStringExtra("selectedList");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("สร้างเนื้อเรื่องใหม่ ");

        myHelper = new DbHelper(this);
        et_name = (EditText) findViewById(R.id.input_story_name);
        et_des = (EditText) findViewById(R.id.input_story_des);
        et_creator = (EditText) findViewById(R.id.input_story_creator);
        saveButton = (Button) findViewById(R.id.button_send);
        this.saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View view){
                    saveStory();
                }
            }
        );
    }

    public void saveStory(){
        this.name = et_name.getText().toString();
        this.description = et_des.getText().toString();
        this.creator = et_creator.getText().toString();

        myHelper.insertStory(name, description, creator, scene);
        this.finish();
    }

}
