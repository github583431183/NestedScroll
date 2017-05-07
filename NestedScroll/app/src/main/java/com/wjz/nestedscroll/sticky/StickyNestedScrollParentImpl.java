package com.wjz.nestedscroll.sticky;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.wjz.nestedscroll.R;

/**
 * Created by Wjz on 2017/5/7.
 *
 * 可以继承NestedScrollView 默认实现 NestedScrollingParent
 *
 * @issue viewpager未填充整个布局，底部有空白，留有一个indicator的高度
 */

public class StickyNestedScrollParentImpl extends LinearLayout implements NestedScrollingParent {

    private final NestedScrollingParentHelper parentHelper;
    private final OverScroller scroller;
    private final int touchSlop;
    private final int maximumFlingVelocity;
    private final int minimumFlingVelocity;
    private View top;
    private View indicator;
    private View viewpager;
    private int topMeasuredHeight;

    public StickyNestedScrollParentImpl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 默认 铅直方向
        setOrientation(LinearLayout.VERTICAL);

        // parentHelper
        parentHelper = new NestedScrollingParentHelper(this);

        // 滑动组件
        scroller = new OverScroller(getContext());

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        maximumFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    /**
     * 布局完成
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        top = findViewById(R.id.top_view);
        indicator = findViewById(R.id.indicator_view);
        viewpager = findViewById(R.id.viewpager);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 测量容器中布局高度
        topMeasuredHeight = top.getMeasuredHeight();

        // 上面测量的结果是viewpager的高度只能占满父控件的剩余空间
        // 重新设置viewpager的高度
        ViewGroup.LayoutParams layoutParams = viewpager.getLayoutParams();
        // 重新测量viewpager的高度，空出indicator的高度，完成Sticky
        layoutParams.height = getMeasuredHeight()-indicator.getMeasuredHeight();
        viewpager.setLayoutParams(layoutParams);
    }

    @Override
    public void scrollTo(int x, int y) {
        //限制滚动范围
        if (y < 0) {
            y = 0;
        }
        if (y > topMeasuredHeight) {
            y = topMeasuredHeight;
        }

        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 惯性滑动
     * @param velocityY
     */
    public void fling(int velocityY) {
        scroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, topMeasuredHeight);
        invalidate();
    }


    //实现NestedScrollParent接口-------------------------------------------------------------------------

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < topMeasuredHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    //boolean consumed:子view是否消耗了fling
    //返回值：自己是否消耗了fling。可见，要消耗只能全部消耗
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("onNestedFling", "called");
        return false;
    }

    //返回值：自己是否消耗了fling。可见，要消耗只能全部消耗
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e("onNestedPreFling", "called");
        if (getScrollY() < topMeasuredHeight) {
            fling((int) velocityY);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }
}
