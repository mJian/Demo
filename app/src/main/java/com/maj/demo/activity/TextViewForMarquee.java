package com.maj.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.maj.demo.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class TextViewForMarquee extends AppCompatActivity {

    @ViewInject(R.id.tv_marquee)
    TextView tvMarquee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_for_marquee);

        x.view().inject(this);

        //以下属性必须在代码中设置 xml中定义无效
        tvMarquee.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvMarquee.setSingleLine();
    }
}
