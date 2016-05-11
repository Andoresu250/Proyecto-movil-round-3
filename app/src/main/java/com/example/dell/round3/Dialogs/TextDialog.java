package com.example.dell.round3.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.round3.DB.DataBase;
import com.example.dell.round3.DB.TData;
import com.example.dell.round3.FirebaseModels.Activity;
import com.example.dell.round3.Activity.Maps.ActivityMapFragment;
import com.example.dell.round3.Login.CurrentUser;
import com.example.dell.round3.R;

public class TextDialog extends DialogFragment {

    Activity activity;
    CurrentUser user;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        activity = (Activity) args.getSerializable("activity");
        user = new CurrentUser(getActivity().getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Texto");
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_text, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText text = (EditText) view.findViewById(R.id.text);
                                DataBase db = new DataBase(getActivity().getApplicationContext());
                                TData textD = new TData(activity.getName(), user.getName(),text.getText().toString(),"text");
                                db.insertData(textD);
                                ActivityMapFragment activityMapFragment = (ActivityMapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map) ;
                                activityMapFragment.setTextMarker();
                                Toast.makeText(getActivity(), "Texto guardado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
