package com.maj.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;

import com.maj.demo.R;
import com.maj.demo.adapter.TestAdapter;
import com.maj.demo.util.ToolsUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class RecyclerViewActivity extends AppCompatActivity {

    private Context mContext;
    @ViewInject(R.id.rv_test)
    RecyclerView rvTest;

    private LinearSnapHelper helper;
    private LinearLayoutManager manager;
    private boolean isFirst = true; // 两边视图scale
    private int mOnePageWidth;
    private int offset = 0;
    private int mCurrentItemPos = 7000;
    private int movePage = 0;//划过的item个数
    private int padding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mContext = this;
        x.view().inject(this);
        mOnePageWidth= ToolsUtil.dip2px(mContext, 60);
        padding = ToolsUtil.dip2px(mContext, 10);

        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTest.setLayoutManager(manager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        rvTest.addItemDecoration(itemDecoration);

        //解决adapter里 调用notifyItemChanged()闪烁的问题
        ((SimpleItemAnimator)rvTest.getItemAnimator()).setSupportsChangeAnimations(false);

        //居中效果
        helper = new LinearSnapHelper();
        helper.attachToRecyclerView(rvTest);

        int[] ids = {R.drawable.feel_it, R.drawable.live_it, R.drawable.pay_it, R.drawable.play_it,
                R.drawable.rock_it, R.drawable.secure_it, R.drawable.see_it, R.drawable.taste_it};
        final TestAdapter adapter = new TestAdapter(mContext, ids);
        adapter.setOnItemClickListener(new TestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Log.i("RecyclerViewActivity", "OnItemClick position=" + position);
            }
        });
        rvTest.setAdapter(adapter);
        rvTest.scrollToPosition(mCurrentItemPos);
        rvTest.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动停止
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    //获取居中View
                    View cenView = helper.findSnapView(manager);
                    //获取居中的position
                    int cenPosition = recyclerView.getChildAdapterPosition(cenView);
//                    //设置居中变大
                    adapter.setCenterPosition(cenPosition);
                    mCurrentItemPos = cenPosition;
                    offset = 0;
                    rvTest.getLayoutManager().findViewByPosition(cenPosition + 1).setPadding(padding,padding,padding,padding);
                    rvTest.getLayoutManager().findViewByPosition(cenPosition - 1).setPadding(padding,padding,padding,padding);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirst){
                    return;
                }
                offset += dx;
                try {
                    onScrolledChangedCallback(dx);
                }catch (Exception e){
                    movePage = offset/mOnePageWidth;
                    int position = mCurrentItemPos + movePage;
                    Log.i("RecyclerViewActivity", "Exception position=" + position + " ,offset=" + offset);
                    e.printStackTrace();
                }
            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View cenView = helper.findSnapView(manager);
                //获取居中的position
                int cenPosition = rvTest.getChildAdapterPosition(cenView);
                //设置居中变大
                adapter.setCenterPosition(cenPosition);
                mCurrentItemPos = cenPosition;
                offset = 0;
                isFirst = false;
            }
        }, 200);

    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback(int dx) {
        movePage = offset/mOnePageWidth;
        int position = mCurrentItemPos + movePage;

        float percent = (float) Math.max((Math.abs(offset) - Math.abs(movePage) * mOnePageWidth) * 1.0 / mOnePageWidth, 0.0001);

//        LogUtils.i("RecyclerViewActivity", "position="+position+",mCurrentItemPos="+ mCurrentItemPos + ",offset="+offset + ",percent"+ percent);
        int pad = (int) (padding* percent);

        View leftView = null;
        View currentView;
        View rightView = null;
        if (position > 0) {
            leftView = rvTest.getLayoutManager().findViewByPosition(position - 1);

        }
        currentView = rvTest.getLayoutManager().findViewByPosition(position);
        if (position < rvTest.getAdapter().getItemCount() - 1) {
            rightView = rvTest.getLayoutManager().findViewByPosition(position + 1);
        }

        if (leftView != null && dx<0) {
            leftView.setPadding(padding-pad, padding-pad, padding-pad, padding-pad);
        }
//        if (currentView != null) {
            currentView.setPadding(pad,pad,pad,pad);
//        }
        if (rightView != null && dx >0) {
            rightView.setPadding(padding-pad, padding-pad, padding-pad, padding-pad);
        }

    }
}
