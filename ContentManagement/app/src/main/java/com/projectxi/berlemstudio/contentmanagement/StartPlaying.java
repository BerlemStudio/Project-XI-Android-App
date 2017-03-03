package com.projectxi.berlemstudio.contentmanagement;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.projectxi.berlemstudio.contentmanagement.Adapter.MyAdapter;
import com.projectxi.berlemstudio.contentmanagement.Adapter.StartingAdapter;
import com.projectxi.berlemstudio.contentmanagement.dialog.startDialog;
import com.projectxi.berlemstudio.contentmanagement.res.Scene;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by patawat on 3/3/2017 AD.
 */

public final class StartPlaying {

    public static boolean start(StartingAdapter mAdapter, Activity activity){
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

        String input = "{"+"\"orderArray\""+":"+ Arrays.toString(arrayOrder)+"}";
        dialog.setDialog(order, input, activity);
        dialog.show(activity.getFragmentManager(),"StartWarning");
        return true;
    }
}
