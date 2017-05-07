package com.wjz.nestedscroll.coordinator.DefineBehavior.ClickMove;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.wjz.nestedscroll.coordinator.Behavior;

/**
 * Created by Wjz on 2017/5/7.
 */

public class DefineClickMoveBehavior extends Behavior<View> {

    /**
     * 必须要重载,在CoordinatorLayout里利用反射去获取这个Behavior的时候就是拿的这个构造。
     *
     * @param context
     * @param attrs
     */
    public DefineClickMoveBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param parent     当前的CoordinatorLayout
     * @param child      设置这个Behavior的View
     * @param dependency 关心的那个View
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        /*
        * 设置关心的view
        * 此处关心TextView
        * */
        if (dependency instanceof TextView) {
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 被关心的View 改变后，改变child
     *
     * @param parent     当前的CoordinatorLayout
     * @param child      设置这个Behavior的View
     * @param dependency 关心的那个View
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        int offset = dependency.getTop() - child.getTop();

        ViewCompat.offsetTopAndBottom(child, offset);

        return true;
    }
}
