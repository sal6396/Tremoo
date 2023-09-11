package com.example.tremoo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Video_Activity extends AppCompatActivity {

    private static final String EXTRA_VIDEO_URI = "videoUri";

    public static void startWithVideo(Context context, Uri videoUri) {
        Intent intent = new Intent(context, Video_Activity.class);
        intent.putExtra(EXTRA_VIDEO_URI, videoUri);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView videoView = findViewById(R.id.buttonVideo);
        Button videoButton = findViewById(R.id.button2);

        Uri videoUri = getIntent().getParcelableExtra(EXTRA_VIDEO_URI);
        if (videoUri != null) {
            videoView.setVideoURI(videoUri);
            videoView.setMediaController(new MediaController(this));
            videoView.start();
        }

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Video_Activity.this, "Video Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
