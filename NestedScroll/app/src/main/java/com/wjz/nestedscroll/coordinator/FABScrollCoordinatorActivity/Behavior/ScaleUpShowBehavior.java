package com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;

import com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity.AnimatorUtil;

/**
 * 创建人：Wjz
 * 创建时间：2016/9/6
 * 类描述： 上拉显示，下拉隐藏
 */
public class ScaleUpShowBehavior extends FloatingActionButton.Behavior {

    private boolean isAnimatingOut = false;

    public ScaleUpShowBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        System.out.println("onNestedScroll- dxConsumed:"
                + dxConsumed + "; dyConsumed: " + dyConsumed + "; dxUnconsumed: "
                + dxUnconsumed + "; dyUnconsumed: " + dyUnconsumed);

        if (dyConsumed > 0 && dyUnconsumed == 0) {
            System.out.println("上滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            System.out.println("到边界了还在上滑。。。");
        }
        if (dyConsumed < 0 && dyUnconsumed == 0) {
            System.out.println("下滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed < 0) {
            System.out.println("到边界了，还在下滑。。。");
        }

        if (((dyConsumed > 0 && dyUnconsumed == 0) ||
                (dyConsumed == 0 && dyUnconsumed > 0)) && child.getVisibility() != View.VISIBLE) {
            AnimatorUtil.scaleShow(child, null);// 显示
        } else if (((dyConsumed < 0 && dyUnconsumed == 0) ||
                (dyConsumed == 0 && dyUnconsumed < 0)) && child.getVisibility() != View.GONE && !isAnimatingOut) {
            AnimatorUtil.scaleHide(child, viewPropertyAnimatorListener);// 隐藏
        }
    }

    private ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {

        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.GONE);// 隐藏
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };
}
