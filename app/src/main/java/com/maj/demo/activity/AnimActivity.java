package com.maj.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.maj.demo.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class AnimActivity extends AppCompatActivity {

    @ViewInject(R.id.img_anim)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        x.view().inject(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.my_anim);
        animation.setDuration(3000);
        img.startAnimation(animation);
    }
}
