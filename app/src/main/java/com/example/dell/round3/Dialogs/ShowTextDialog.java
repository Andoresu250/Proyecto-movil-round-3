package com.example.dell.round3.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ShowTextDialog extends DialogFragment {

    private String text;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        text = args.getString("text");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Texto")
                .setIcon(
                        getResources().getDrawable(
                                android.R.drawable.ic_menu_edit))
                .setMessage(text);


        return builder.create();
    }
}
