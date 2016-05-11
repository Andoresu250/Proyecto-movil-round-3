package com.example.dell.round3.Dialogs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dell.round3.R;
import com.example.dell.round3.UpdateAudio.Audio;

public class AudioDialog extends DialogFragment {

    private String audioUrl;
    private Audio audio;
    private ProgressDialog dialog;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        audioUrl = args.getString("url");
        audio = new Audio();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_audio, null);
        ImageButton playBtn = (ImageButton) view.findViewById(R.id.playBtn);
        ImageButton stopBtn = (ImageButton) view.findViewById(R.id.pauseBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(getContext(), "", "Descargando Audio...", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        audio.reproduce(audioUrl, dialog);
                    }
                }).start();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.Stop();
            }
        });
        builder.setView(view)
                .setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        return builder.create();
    }
}
