package com.ai.tremoo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Audio_Activity extends AppCompatActivity {

    private static final int RECORD_AUDIO_PERMISSION_REQUEST_CODE = 1001;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    ImageView ivRecord;
    ImageView ivPlay;
    TextView tvTime;
    TextView tvRecordingPath;
    ImageView ivSimplebg;
    Boolean isRecording = false;
    boolean isPlaying = false;
    int second = 0;
    String path = null;
    LottieAnimationView lavPlaying;
    int dummySecond = 0;
    int playableSecond = 0;

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler;
    ImageView btnback;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
//       getSupportActionBar().hide();


        ivRecord = findViewById(R.id.iv_Record);
        ivPlay = findViewById(R.id.iv_play);
        tvTime = findViewById(R.id.tv_Time);
        tvRecordingPath = findViewById(R.id.tv_recording_Path);
        ivSimplebg = findViewById(R.id.simple_Bg);
        lavPlaying = findViewById(R.id.lav_playing);
        mediaPlayer = new MediaPlayer();



//        btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Audio_Activity.this, LoginActivity.class));
//            }
//        });

        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkRecordingPermission()){
                    if(!isRecording)
                    {
                        isRecording=true;
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                mediaRecorder = new MediaRecorder();
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                                mediaRecorder.setOutputFile(getRecordingFilePath());
                                path=getRecordingFilePath();
                                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                                try {
                                    mediaRecorder.prepare();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                mediaRecorder.start();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivSimplebg.setVisibility(View.VISIBLE);
                                        lavPlaying.setVisibility(View.VISIBLE);
                                        tvRecordingPath.setText(getRecordingFilePath());
                                        playableSecond=0;
                                        second= 0;
                                        dummySecond= 0;
                                        ivRecord.setImageDrawable(ContextCompat.getDrawable(Audio_Activity.this,R.drawable.active));
                                        runTimer();
                                    }
                                });
                            }
                        });
                    }
                    else {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                mediaRecorder.stop();
                                mediaRecorder.release();
                                mediaRecorder=null;
                                playableSecond=second;
                                dummySecond=second;
                                second=0;
                                isRecording=false;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivSimplebg.setVisibility(View.VISIBLE);
                                        lavPlaying.setVisibility(View.VISIBLE);
                                       handler.removeCallbacksAndMessages(null);
                                        ivRecord.setImageDrawable(ContextCompat.getDrawable(Audio_Activity.this,R.drawable.inactive));
                                    }
                                });
                            }
                        });
                    }
                }
                else
                {
                    requestRecordingPermission();
                }
            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlaying){
                    if(path !=null){
                        try {
                            mediaPlayer.setDataSource(getRecordingFilePath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "No Recording Present", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mediaPlayer.start();
                    isPlaying =true;
                    ivPlay.setImageDrawable(ContextCompat.getDrawable(Audio_Activity.this,R.drawable.pause));
                    ivSimplebg.setVisibility(View.GONE);
                    lavPlaying.setVisibility(View.VISIBLE);
                    runTimer();
                }
                else {
                   mediaPlayer.stop();
                   mediaPlayer.release();
                   mediaPlayer=null;
                   mediaPlayer = new MediaPlayer();
                   isPlaying = false;
                   second=0;
                   handler.removeCallbacksAndMessages(null);
                    ivSimplebg.setVisibility(View.VISIBLE);
                    lavPlaying.setVisibility(View.GONE);
                    ivPlay.setImageDrawable(ContextCompat.getDrawable(Audio_Activity.this,R.drawable.play));
                }
            }
        });

    }

    private void runTimer()
    {
        handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes=(second%3600)/60;
                int secs=second%60;
                String time = String.format(Locale.getDefault(),"%02d:%02d",minutes,secs);
                tvTime.setText(time);

                if(isRecording ||(isPlaying && playableSecond!=-1)){
                    second++;
                    playableSecond--;

                    if(playableSecond==-1 && isPlaying){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                        mediaPlayer= new MediaPlayer();
                        playableSecond=dummySecond;
                        second = 0;
                        handler.removeCallbacksAndMessages(null);
                        ivSimplebg.setVisibility(View.VISIBLE);
                        lavPlaying.setVisibility(View.GONE);
                        ivPlay.setImageDrawable(ContextCompat.getDrawable(Audio_Activity.this,R.drawable.play));
                        return;

                    }
                }

                handler.postDelayed(this,100);
            }
        });
    }

    private void requestRecordingPermission()
    {
        ActivityCompat.requestPermissions(Audio_Activity.this,new String[]{Manifest.permission.RECORD_AUDIO},RECORD_AUDIO_PERMISSION_REQUEST_CODE);
    }

    public Boolean checkRecordingPermission()
    {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED)
        {
            requestRecordingPermission();
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==RECORD_AUDIO_PERMISSION_REQUEST_CODE){
            if(grantResults.length>0){
                Boolean permissionToRecord=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if(permissionToRecord){
                    Toast.makeText(this, "Permission Given", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File music=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(music,"testfile"+".mp3");
        return file.getPath();
    }
}
