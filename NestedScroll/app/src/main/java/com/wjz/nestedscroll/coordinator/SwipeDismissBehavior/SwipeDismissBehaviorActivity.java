package com.wjz.nestedscroll.coordinator.SwipeDismissBehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wjz.nestedscroll.R;

/**
 * 创建人：Wjz
 * 创建时间：2016/9/6
 * 类描述： 使用SwipeDismissBehavior 完成滑动消失
 */
public class SwipeDismissBehaviorActivity extends AppCompatActivity {
    private SwipeDismissBehavior swipeDismissBehavior;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_dismiss_behavior_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeDismissBehavior = new SwipeDismissBehavior();

        // 属性设置
        swipeDismissBehavior.setListener(onDismissListener);
        swipeDismissBehavior.setDragDismissDistance(0.5F);
        swipeDismissBehavior.setStartAlphaSwipeDistance(0F);
        swipeDismissBehavior.setEndAlphaSwipeDistance(0.5F);
        swipeDismissBehavior.setSensitivity(0);
        // 设置滑动方向后 消失
        //swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);

        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) findViewById(R.id.textview).getLayoutParams();
        coordinatorParams.setBehavior(swipeDismissBehavior);


    }

    private SwipeDismissBehavior.OnDismissListener onDismissListener = new SwipeDismissBehavior.OnDismissListener() {
        @Override
        public void onDismiss(View view) {
        }

        @Override
        public void onDragStateChanged(int state) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
