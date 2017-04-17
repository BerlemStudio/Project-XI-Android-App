package com.projectxi.berlemstudio.contentmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repassword;
    private EditText email;


    private Button submite;
    private Button cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.username = (EditText) findViewById(R.id.username);
        this.password = (EditText) findViewById(R.id.password);
        this.repassword = (EditText) findViewById(R.id.confirmPassword);
        this.email = (EditText) findViewById(R.id.email);

        submite = (Button) findViewById(R.id.submit);
        cancle = (Button) findViewById(R.id.cancle);

        submite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                finish();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


    }
}
