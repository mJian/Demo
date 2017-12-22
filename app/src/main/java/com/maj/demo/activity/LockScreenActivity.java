package com.maj.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.maj.demo.R;

public class LockScreenActivity extends Activity {

    private Context mContext;
    private WindowManager wm;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        params.format = PixelFormat.TRANSPARENT;
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;;
        params.flags=WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;

        mView= LayoutInflater.from(this).inflate(R.layout.activity_lock_screen, null);
        mView.findViewById(R.id.btn_lock_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wm!=null){
                    wm.removeView(mView);
                }
                finish();
            }
        });

        wm.addView(mView, params);

        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

    }
}
