package com.example.dell.round3;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecording implements Serializable {

    private MediaRecorder myAudioRecorder;
    private MediaPlayer mediaPlayer = null;
    private String outputFile = null;

    private static final String AUDIO_RECORDER_FOLDER = "Proyecto/AudioRecorder";
    private static final String AUDIO_RECORDER_FILE_EXT = ".mp3";

    public AudioRecording() {

    }

    public void starRecording(){
        try {
            if(mediaPlayer != null){ if(mediaPlayer.isPlaying()){ mediaPlayer.stop(); } }

            outputFile = getFilename();
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            myAudioRecorder.setOutputFile(outputFile);
            myAudioRecorder.prepare();
            myAudioRecorder.start();

        } catch (IOException e) {e.printStackTrace(); }

    }

    public void stopRecording(){
        if( myAudioRecorder != null){
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder  = null;
        }
    }

    public void playAudio(String audioName){

        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        audioName = file.getAbsolutePath() + "/" + audioName;

        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioName);
            mediaPlayer.prepare();
            mediaPlayer.start();

        }

        catch (IOException e) { e.printStackTrace(); }
    }

    public  void stopAudio(){
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        }
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "AUD_" + timeStamp;

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + audioFileName +  AUDIO_RECORDER_FILE_EXT);
    }

}
