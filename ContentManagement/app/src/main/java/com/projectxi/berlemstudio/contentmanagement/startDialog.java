package com.projectxi.berlemstudio.contentmanagement;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by patawat on 1/20/2017 AD.
 */

public class startDialog extends DialogFragment {
    private String text;
    private Activity activity;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public void setDialog(String text, Activity activity){
        this.text = text;
        this.activity = activity;
    }
    public void setText(String text){
        this.text = toJsonString(text);
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.text).setPositiveButton("yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                writeFile();

                Intent intent = new Intent();
                intent.setClassName("com.BerlemStudio.ProjectXI","com.unity3d.player.UnityPlayerActivity");
                startActivity(intent);
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }
    public String toJsonString(String json){
        json = "{"+json+"}";
        return json;
    }
    public void writeFile(){
        String filename = "storyOrder.json";
        this.verifyStoragePermissions();
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();

        File dir = new File(root, "spaceTour");
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"order.json");
        if (file.exists ()) {
            file.delete ();
        }else {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(this.text.getBytes());
                out.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void verifyStoragePermissions(){
        int permistion = ContextCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permistion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }

}
