package com.projectxi.berlemstudio.contentmanagement.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.projectxi.berlemstudio.contentmanagement.convertArrays;
import com.projectxi.berlemstudio.contentmanagement.model.DbHelper;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by patawat on 1/20/2017 AD.
 */

public class startDialog extends DialogFragment {
    private String text;
    private Activity activity;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private DbHelper myHelper;
    private String[] order;
    public void setDialog(String[] order, String text, Activity activity){
        this.text = text;
        this.activity = activity;
        this.order = order;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        Context context = getContext();
        myHelper = new DbHelper(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("คุณต้องการที่จะเริ่มหรือไม่").setPositiveButton("ใช่",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                saveLastStart("การเล่นครั้งล่าสุด", "เนื้อหาที่ใช้เขาดูครั้งล่าสุด", "Auto save", order);
                writeFile();
                Intent intent = new Intent();
                intent.setClassName("com.BerlemStudio.ProjectXI","com.unity3d.player.UnityPlayerActivity");
                startActivity(intent);
            }
        }).setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        return builder.create();
    }

    // Write Json file
    public void writeFile(){
        this.verifyStoragePermissions();
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();

        File dir = new File(root, "spaceTour");
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"order.json");
        if (file.exists ()) {
            file.delete ();
        }

            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(this.text.getBytes());
                out.close();
            } catch (Exception e){
                e.printStackTrace();
            }

    }

    // Save last ordering play list in database
    public void saveLastStart(String story,String des,String creator, String[] order){
        convertArrays convertor = new convertArrays();
        String convert = convertor.convertArrayToString(order);
        myHelper.insertStory(story , des, creator, convert);
        Log.d("saveStory", "saveLastStart: True");
    }

    // Verify permissions because write SD storage
    public void verifyStoragePermissions(){
        int permistion = ContextCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permistion!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},this.REQUEST_EXTERNAL_STORAGE);
        }
    }
}
