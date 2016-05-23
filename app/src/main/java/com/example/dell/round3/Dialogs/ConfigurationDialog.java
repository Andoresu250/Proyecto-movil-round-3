package com.example.dell.round3.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import com.example.dell.round3.Login.CurrentUser;
import com.example.dell.round3.R;

public class ConfigurationDialog extends DialogFragment {

    CurrentUser user;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        user = new CurrentUser(getActivity().getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Configuracion de datos");
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.switch_layout, null);
        final Switch mSwitch = (Switch) view.findViewById(R.id.switchForActionBar);
        mSwitch.setChecked(user.getActivate());
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.setActivate(mSwitch.isChecked());
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}