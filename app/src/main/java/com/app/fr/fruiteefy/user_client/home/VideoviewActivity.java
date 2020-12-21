package com.app.fr.fruiteefy.user_client.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.fr.fruiteefy.R;

public class VideoviewActivity extends AppCompatActivity {
    VideoView videoView;
 String video_url;
 ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_videoview);


        if (getIntent().hasExtra("videourl")) {

            video_url = getIntent().getStringExtra("videourl");
            videoView = findViewById(R.id.videoview);

            Log.d("dsdasdsd", getIntent().getStringExtra("videourl"));
//
            Uri video = Uri.parse(video_url);
//
//
//            MediaController mediaController = new MediaController(VideoviewActivity.this);
//            mediaController.setAnchorView(mediaController);
//            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            progressDialog = ProgressDialog.show( this, null, null, false, true );
            progressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            progressDialog.setContentView( R.layout.progress_bar);
//            progressDialog = ProgressDialog.show(this,R.style.AppCompatAlertDialogStyle);
//            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//
//                    videoView.start();
//                }
//            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                }
            });

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                    progressDialog.dismiss();
                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                            MediaController mediaController = new MediaController(VideoviewActivity.this);
                            videoView.setMediaController(mediaController);
                            mediaController.setAnchorView(videoView);
                        }
                    });
                }
            });

            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    return false;
                }
            });
        }

    }
}




