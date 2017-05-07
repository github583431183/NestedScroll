# NestedScroll

## NestedScrollingChild接口 & NestedScrollingParent接口 
* Android5提供处理滑动冲突的2个接口，底层实现
* child 布局 会在startNestedScroll() 方法中 用NestedScrollingChildHelper帮助类 查找 最近实现NestedScrollingParent接口的ViewGroup，组合实现嵌套滑动
### StickActivity
* 自定义实现NestedScrollingParent接口的ViewGroup 实现sticky功能

### NestedScrollImplActivity 
* 自定义NestedScrollingChild接口 & NestedScrollingParent接口 实现sticky功能

## CollapsingToolbarLayout 折叠布局 

        CollapsingToolbarLayout作用是提供了一个可以折叠的Toolbar，
        它继承至FrameLayout，给它设置layout_scrollFlags，
        它可以控制包含在CollapsingToolbarLayout中的控件
        (如：ImageView、Toolbar)在响应layout_behavior事件时
        作出相应的scrollFlags滚动事件(移除屏幕或固定在屏幕顶端)

        layout_scrollFlags:参数
        scroll – 想滚动就必须设置这个。
        enterAlways – 实现quick return效果, 当向下移动时，立即显示View（比如Toolbar)。
        exitUntilCollapsed – 向上滚动时收缩View，但可以固定Toolbar一直在上面。
        enterAlwaysCollapsed – 当View已经设置minHeight属性又使用此标志时，
                                View只能以最小高度进入，只有当滚动视图到达顶部时才扩大到完整高度。

        contentScrim – 设置当完全CollapsingToolbarLayout折叠(收缩)后的背景颜色。
        expandedTitleMarginStart – 设置扩张时候(还没有收缩时)title向左填充的距离。

## CoordinatorLayout 协同布局
* CoordinateLayout是FrameLayout的扩展
* 作为容器，子View之间的相互交互
### 使用方法

* 通过为CoordinatorLayout的子views指定Behavior，在同一个父容器下可以提供不同的交互，并且那些子view可以和另一个子view相互作用，相互影响。
* 通过`@DefaultBehavior注释`，CoordinatorLayout的子view可以使用一个默认的behavior。
* CoordinatorLayout的子view也许会有个anchor（锚点，即view显示在哪块区域），这个子view必须是CoordinatorLayout的直接子view，不能是子孙View

### CoordinatorLayout.Behavior

* Behavior可以用来实现各种交互和 来自滑动抽屉、滑动删除元素和按钮关联其他元素产生的额外的布局修改。
* 官方给出4个Behavior实现类
	* BottomSheetBehavior
		* 一般用于底部弹出框，类似于微信支付的效果
		* 列如：BottomSheet控件
	* SwipeDismissBehavior
		* 官方实现为Snackbar，已经封装好，唯一的条件是根布局必须为CoordinatorLayout，否则没有效果。
		* 例如：滑动消失
	* FloatingActionButton.Behavior
		* FloatingActionButton默认使用FloatingActionButton.Behavior，同Snackbar一样，唯一需要注意的是根布局必须为CoordinatorLayout。
	* ViewOffsetBehavior
		* 官方实现为AppBarLayout中的Beihavior。

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


### 自定义Behavior
*  自定义Behavior一般有两种情况：
	*  某个view监听另一个view的状态变化，例如大小、位置、显示状态等。
	*  某个view监听CoordinatorLayout里的滑动状态。

#### 某个view监听另一个view的状态变化，例如大小、位置、显示状态等。
* 例如：FloatingActionButton+Snackbar
	* 需重写方法 layoutDependsOn()和onDependentViewChanged()

#### 某个view监听CoordinatorLayout里的滑动状态。
* 例如:AppBarLayout的显示隐藏
	* 需重写方法 onStartNestedScroll()和onNestedPreScroll()
	

## AppBarLayout
* AppBarLayout是一个实现了很多Material设计风格的垂直的LinearLayout。
* AppBarLayout中的子view可以通过设置layout_scrollFlags来提供他们所需要的滚动方式。
* 需要设置Scrolling view的behavior（app:layout_behavior）为AppBarLayout.ScrollingViewBehavior来提醒AppBarLayout什么时候滚动。
> app:layout_behavior="@string/appbar_scrolling_view_behavior"

### layout_scrollFlags 属性
* SCROLL_FLAG_SCROLL：
	* 对应xml布局中的scroll，如果要设置其他的滚动flag，这个flag必须要设置，否则无效。

![scroll](http://upload-images.jianshu.io/upload_images/1215918-4bb1c9b9ef5e0bdb.gif?imageMogr2/auto-orient/strip)

* SCROLL_FLAG_EXIT_UNTIL_COLLAPSED：
	* 对应xml布局中的exitUntilCollapsed，设置该flag的view在向上滑动退出屏幕时，不会完全退出，会保留collapsed height（minHeight）高度。

![scroll+exitUntilCollapsed](http://upload-images.jianshu.io/upload_images/1215918-8df8e86ff727877c.gif?imageMogr2/auto-orient/strip)

* SCROLL_FLAG_ENTER_ALWAYS：
	* 对应xml布局中的enterAlways，只要手指向下滑，设置该flag的view就会直接进入屏幕，不管NestedScrollView是否在滚动。

![](scroll+enterAlways)

* SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED ：
	* 对应xml布局中的enterAlwaysCollapsed，是enterAlways的附加flag，使设置该flag的view在进入屏幕时最初只滑动显示到它的collapsed height（minHeight），一旦NestedScrollView滑到顶部，该view再滑动显示剩余的部分。

![scroll+enterAlways+enterAlwaysCollapsed](http://upload-images.jianshu.io/upload_images/1215918-946022ce80784e08.gif?imageMogr2/auto-orient/strip)

* SCROLL_FLAG_SNAP：
	* 对应xml布局中的snap，设置该flag的view在滚动停止时，如果没有完全显示，会自动滚到到最近的一个边界（顶端、中线和下线）。
> 滚动结束后，如果视图仅部分可见，那么它将被捕捉并滚动到最接近的边缘。 例如，如果视图仅显示底部的25％，则将完全滚动屏幕。 相反，如果底部的75％是可见的，那么它将被完全滚动到视图中。

![scroll+enterAlways+snap](http://upload-images.jianshu.io/upload_images/1215918-22c675c74d224a67.gif?imageMogr2/auto-orient/strip)

# Android 5 style

	<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- 标题栏背景色-->
        <item name="colorPrimary">@color/colorPrimary</item>

        <!-- 状态栏的背景色-->
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>

        <!-- 各控制元件(如：check box、switch 或是 radio) 被勾选 (checked) 或是选定 (selected) 的颜色。-->
        <item name="colorAccent">@color/colorAccent</item>

        <!-- App 的背景色-->
        <item name="android:windowBackground">@color/windowBackground</item>

        <item name="windowActionModeOverlay">true</item>
        <!--action Mode背景-->
        <item name="actionModeBackground">@color/theme_color_action_mode</item>

        <!--navigationBar的背景色，但只能用在 API Level 21 (Android 5) 以上的版本

        <item name="android:navigationBarColor">@android:color/holo_green_dark</item>
        -->
    </style>

    <!-- 取消标题栏-->
    <style name="AppTheme.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>


       <!--statusBar的背景色，但只能用在 API Level 21 (Android 5) 以上的版本

       <item name="android:statusBarColor">@android:color/transparent</item>-->
    </style>

    <!--menu样式和AppBarLayout样式 覆盖物的颜色-->
    <style name="AppTheme.AppBarOverlay" parent="ThemeOverlay.AppCompat.Dark.ActionBar">
    </style>

    <!-- 标题栏的字体样式-->
    <style name="AppTheme.PopupOverlay" parent="ThemeOverlay.AppCompat.Light">
        <!-- 用来定义标题栏文字颜色,setTitle时使用，自定义的toolbar中的textView不起效果-->
        <item name="android:textColorPrimary">@color/textColorPrimary</item>
        <!-- 用来定义弹出菜单icon的颜色(竖着排列的那三个点颜色，以及返回按钮等)-->
        <item name="android:textColorSecondary">@color/textColorSecondary</item>
        <!-- 用来定义弹出菜单OptionMenu文字的颜色，自定义的toolbar中的textView有效果-->
        <item name="android:textColor">@color/colorAccent</item>
    </style>

	<!-- v21-->
    <style name="AppTheme.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
        <!-- getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);-->
        <item name="android:windowDrawsSystemBarBackgrounds">true</item>
        <!-- statusBar的背景色，但只能用在 API Level 21 (Android 5) 以上的版本-->
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
