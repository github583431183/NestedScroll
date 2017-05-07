package com.wjz.nestedscroll.coordinator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Wjz on 2017/5/7.
 *
 * CoordinatorLayout.Behavior 的使用介绍
 */

public class Behavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    /* 构造方法*/
    public Behavior() {
    }

    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //-----------------------------------单视图--------------------------------------------------
    //-----------------------------------拦截触摸事件--------------------------------------------------------

    /**
     * 通过Behavior的onInterceptTouchEvent()，
     * 将调用传递给它的onInterceptTouchEvent()，
     * 让Behavior获得拦截触摸事件的机会。
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    /**
     * onInterceptTouchEvent返回为true，
     * Behavior会通过onTouchEvent()接收后续的所有触摸事件，
     * 而且无需视图了解后续情况。
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    /**
     * 拦截任何交互(高级触摸拦截)
     * 返回true
     * 在互动被拦截时也许你会希望有些视觉信号提示（以免使用者以为应用完全不能用了）
     * 这就是为什么blocksInteractionBelow()的默认功能实际上依赖于getScrimOpacity()值
     * 返回非零值会为视图提供一层颜色遮罩（用getScrimColor()来确定颜色，默认为黑），并立即禁用所有的触摸互动.
     *
     * @param parent
     * @param child
     * @return
     */
    @Override
    public boolean blocksInteractionBelow(CoordinatorLayout parent, V child) {
        return super.blocksInteractionBelow(parent, child);
    }

    @Override
    public int getScrimColor(CoordinatorLayout parent, V child) {
        return super.getScrimColor(parent, child);
    }

    @Override
    public float getScrimOpacity(CoordinatorLayout parent, V child) {
        return super.getScrimOpacity(parent, child);
    }


    //-----------------------------------拦截window insets--------------------------------------------------------

    /**
     * fitsSystemWindows的实际作用，可归结为：
     * window insets需要避免在系统窗口（比如状态栏和导航栏）之下出现。
     * 这里Behavior也能发挥作用：如果视图为fitsSystemWindows=“true”，
     * 则onApplyWindowInsets()会调用绑定Behavior，且优先级高于视图自身。
     * <p>
     * 注意： 大多情况下，如果Behavior没有消耗掉整个window insets，
     * 则应当通过ViewCompat.dispatchApplyWindowInsets() 来传递这个insets，
     * 以确保视图的任何子项有机会看到这个WindowInsets。
     *
     * @param coordinatorLayout
     * @param child
     * @param insets
     * @return
     */
    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V child, WindowInsetsCompat insets) {
        ViewCompat.dispatchApplyWindowInsets(child, insets);
        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
    }

    //-----------------------------------拦截Measurement和Layout--------------------------------------------------------

    /*
    Measurement和layout是Android绘制视图的关键组件，
    因此Behavior只有在onMeasureChild()和onLayoutChild()回调前拦截父视图的measurement和layout，才能达到预计的效果。
    * */
    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, V child,
                                  int parentWidthMeasureSpec, int widthUsed,
                                  int parentHeightMeasureSpec, int heightUsed) {
        int maxWidth = 100;

        int widthMode = View.MeasureSpec.getMode(parentWidthMeasureSpec);
        int width     = View.MeasureSpec.getSize(parentWidthMeasureSpec);
        if (widthMode == View.MeasureSpec.UNSPECIFIED || width > maxWidth) {
            width = maxWidth;
            widthMode = View.MeasureSpec.AT_MOST;
            parent.onMeasureChild(child,
                    View.MeasureSpec.makeMeasureSpec(width, widthMode),
                    widthUsed, parentHeightMeasureSpec, heightUsed);
            return true;
        }
        return false;

    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    //-----------------------------------多视图 相互依赖--------------------------------------------------
    //-----------------------------------理解视图间的依赖--------------------------------------------------

    /*
    当另一个视图改变时，你的Behavior会获得回调，根据外部情况来变更自身功能。
    Behavior在两种情况下会成为视图的依赖：
    一种是将Behavior相应的视图锚定在另一个视图上时（隐性依赖），
        在视图中使用CoordinatorLayout的layout_anchor属性，就能起到锚定的作用。
        与layout_anchorGravity属性一同使用，就能将两个视图一并有效地固定在某个位置上。
        例如：可以将FloatingActionButton锚定到AppBarLayout上，而在AppBarLayout滚动出屏幕时，
        FloatingActionButton.Behavior就会通过隐性依赖将自身隐藏起来。

    还有一种是在layoutDependsOn()中明确返回true时。

    当依赖视图被移除时，Behavior会获得onDependentViewRemoved()的回调；而只要依赖视图出现变更，Behavior就会获得onDependentViewChanged()的回调（即调整大小或自身位置）。

    注意： 在添加依赖时，视图总是会在依赖视图布局后进行布局，无视子项次序。
    * */
    //-----------------------------------嵌套滚动-------------------------------------------------
    /*
    1.无需在嵌套滚动视图中声明依赖，因为CoordinatorLayout的每个子项都有可能接收到嵌套滚动事件。
    2.嵌套滚动不仅可以在CoordinatorLayout的直接子项中发起，也能在任何子视图（比如CoordinatorLayout的子项的子项的子项中）发起。
    3.虽然我们称之为嵌套滚动，不过实际上包括滚动（按照滚动做1：1的位移）与滑动（flinging）两种动作。
    举个例子：如果想要在向下滚动时隐藏FloatingActionButton，并在向上滚动时显示它，只用重写onStartNestedScroll() 和onNestedScroll()，就像在这个ScrollAwareFABBehavior中看到的那样。
    * */


    /**
     * 通过onStartNestedScroll()来发起感兴趣的嵌套滚动事件吧。
     * 收到滚动轴（例如横向或纵向——使它容易忽略在特定方向上的滚动）后，必须在该方向上返回true，以获得随后的滚动事件。
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /**
     * onStartNestedScroll()返回true之后
     * 在滚动视图获得滚动事件前运行，允许相应Behavior消耗一部分或所有的滚动事件（最后消耗的int[]是一个“外部”参数，在其中指明消耗掉的滚动）。
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    /**
     * onStartNestedScroll()返回true之后
     * 滚动视图在滚动后会调用onNestedScroll()，可以知道滚动了多少view，未消耗掉的（overscroll）数量又有多少。
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 滑动操作
     * pre-fling调用必须要么消耗掉所有的滑动，要么不消耗滑动,没有部分消耗的选项
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    /**
     * 在嵌套滚动（或滑动）停止后，就能获得onStopNestedScroll()的调用。这表示滚动结束：在下一个滚动开始前，等待重新调用onStartNestedScroll()。
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
