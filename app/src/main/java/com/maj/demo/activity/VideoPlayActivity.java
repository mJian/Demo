package com.maj.demo.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.maj.demo.R;
import com.maj.demo.util.LogUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class VideoPlayActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private String TAG = "VideoPlayActivity";
    private Context mContext;

    @ViewInject(R.id.sv_video)
    SurfaceView surfaceView;

    private SurfaceHolder surfaceHolder;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        x.view().inject(this);
        mContext = this;

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);
    }

    @Event(value = {R.id.btn_video_start})
    private void clicks(View view){
        switch (view.getId()){
            case R.id.btn_video_start:
                play();
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.i(TAG, "surfaceCreated");
        play();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtils.i(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.i(TAG, "surfaceDestroyed");
    }

    private void play(){
        mediaPlayer = MediaPlayer.create(mContext, R.raw.test);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtils.i(TAG, "onCompletion");
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();;
                mediaPlayer = null;
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtils.i(TAG, "onPrepared");
                mediaPlayer.start();
                mp.setDisplay(surfaceHolder);
                mediaPlayer.setScreenOnWhilePlaying(true);
                surfaceHolder.setKeepScreenOn(true);
            }
        });
    }
}
