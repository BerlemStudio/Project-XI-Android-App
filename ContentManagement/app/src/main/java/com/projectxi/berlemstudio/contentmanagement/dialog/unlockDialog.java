package com.projectxi.berlemstudio.contentmanagement.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.projectxi.berlemstudio.contentmanagement.R;

/**
 * Created by patawat on 4/23/2017 AD.
 */

public class unlockDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.confirm_unlock, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                })
                .setNegativeButton(R.string.cancle_dialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

    private void unlock(){

    }
}
