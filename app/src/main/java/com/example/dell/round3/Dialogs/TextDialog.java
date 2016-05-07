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

import com.example.dell.round3.LocalDataBase.MyDataBase;
import com.example.dell.round3.LocalDataBase.TFiles;
import com.example.dell.round3.Activity.Maps.MyMapFragment;
import com.example.dell.round3.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextDialog extends DialogFragment {

    int activityId;
    int userId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        activityId = args.getInt("activityId");
        userId = args.getInt("userId");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Texto");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.dialog_text, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText text = (EditText) view.findViewById(R.id.text);
                                String token = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"";
                                TFiles file = new TFiles(activityId,userId,text.getText().toString() ,token, "text");
                                MyDataBase myDataBase = new MyDataBase(getActivity().getApplicationContext());
                                myDataBase.insertFiles(file);
                                MyMapFragment myMapFragment = (MyMapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map) ;
                                myMapFragment.setTextMarket();
                                Toast.makeText(getActivity(), "Texto guardado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
