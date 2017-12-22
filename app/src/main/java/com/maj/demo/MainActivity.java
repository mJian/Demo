package com.maj.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maj.demo.activity.AnimActivity;
import com.maj.demo.activity.LockScreenActivity;
import com.maj.demo.activity.MultiPathActivity;
import com.maj.demo.activity.NormalActivity;
import com.maj.demo.activity.RecyclerViewActivity;
import com.maj.demo.activity.RudeActivity;
import com.maj.demo.activity.TextViewForMarquee;
import com.maj.demo.activity.VideoPlayActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        mContext = this;
    }

    @Event(value = {R.id.btn_recycler_view, R.id.btn_anim, R.id.btn_text_view, R.id.btn_lock_screen, R.id.btn_normal, R.id.btn_rude,
            R.id.btn_canvas_view, R.id.btn_media_player})
    private void clicks(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_recycler_view:
                intent.setClass(mContext, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_anim:
                intent.setClass(mContext, AnimActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_text_view:
                intent.setClass(mContext, TextViewForMarquee.class);
                startActivity(intent);
                break;
            case R.id.btn_lock_screen:
                intent.setClass(mContext, LockScreenActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_normal:
                intent.setClass(mContext, NormalActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_rude:
                intent.setClass(mContext, RudeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_canvas_view:
                intent.setClass(mContext, MultiPathActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_media_player:
                intent.setClass(mContext, VideoPlayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
