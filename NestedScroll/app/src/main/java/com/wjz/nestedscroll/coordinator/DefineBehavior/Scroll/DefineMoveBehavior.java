package com.wjz.nestedscroll.coordinator.DefineBehavior.Scroll;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Wjz on 2017/5/6.
 *
 */

public class DefineMoveBehavior extends CoordinatorLayout.Behavior<View> {

    public DefineMoveBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 当一个CoordinatorLayout的子view准备去开始一个嵌套滑动时调用
     *
     * @param coordinatorLayout 根布局coordinatorLayout
     * @param child             这个behavior所关联的coordinatorLayout的子view
     * @param directTargetChild 包含target 嵌套滚动操作的coordinatorLayout子view
     * @param target            开始嵌套滑动的coordinatorLayout子view。
     * @param nestedScrollAxes  嵌套滚动的轴线。See
     *                          {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                          {@link ViewCompat#SCROLL_AXIS_VERTICAL}
     * @return 如果behavior希望接受这个嵌套滚动，则返回true。
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {

        /*
        * SCROLL_AXIS_VERTICAL: 表示沿垂直轴滚动。
        * */
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 滑动事件
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child,
                                  View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        int targetScrolled = target.getScrollY();
        child.setScrollY(targetScrolled);

    }

    /**
     * 处理滑动惯性
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        ((NestedScrollView) child).fling((int) velocityY);
        return true;
    }


}
