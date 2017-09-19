package com.example.mohammed.mhvideoplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.VideoView;

import java.io.IOException;



public class MainActivity extends AppCompatActivity  implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private Button button;
    private VideoView videoView;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    private static final String VIDEO_PATH = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button)findViewById(R.id.bt);
       // videoView= (VideoView)findViewById(R.id.play_video);
        mSurfaceView = (SurfaceView)findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();


        mSurfaceHolder.addCallback(MainActivity.this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mMediaPlayer.setDataSource(VIDEO_PATH);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(MainActivity.this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
