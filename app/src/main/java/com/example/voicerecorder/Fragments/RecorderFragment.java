package com.example.voicerecorder.Fragments;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voicerecorder.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class RecorderFragment  extends Fragment {
    View view;
    ImageButton btnRec;
    TextView textRecStatus;
    Chronometer timeOfRec;
    GifImageView gifImageView;

    private static String fileName;
    private MediaRecorder recorder;
    boolean isRecording ;
    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VoiceRecordings");


    @Nullable

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recoder,container,false);


        btnRec = view.findViewById(R.id.btnRec);
        textRecStatus = view.findViewById(R.id.txtRecStatus);
        gifImageView = view.findViewById(R.id.gifView);
        timeOfRec = view.findViewById(R.id.timeRec);


        isRecording = false;

        askRuntimePermission();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());

        fileName = path + "/recording_" + date + ".amr";
        if (!path.exists()){
            path.mkdirs();
        }

        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording){
                    try {
                        StartRecording();
                        gifImageView.setVisibility(View.VISIBLE);
                        timeOfRec.setBase(SystemClock.elapsedRealtime());
                        timeOfRec.start();
                        textRecStatus.setText("Recording..");
                        btnRec.setImageResource(R.drawable.ic_baseline_stop_24);
                        isRecording = true;
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Failed to record", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (isRecording){
                    StopRecording();
                    gifImageView.setVisibility(View.GONE);
                    timeOfRec.setBase(SystemClock.elapsedRealtime());
                    timeOfRec.stop();
                    textRecStatus.setText("Start");
                    btnRec.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24);
                    isRecording = false;
                }
            }
        });

        return view;
    }

    private  void StartRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();
    }

    private  void StopRecording(){
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void askRuntimePermission() {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }
}
