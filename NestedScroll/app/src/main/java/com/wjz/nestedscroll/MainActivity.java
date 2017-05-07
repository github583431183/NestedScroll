package com.wjz.nestedscroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String[] label = {
            // 嵌套滑动底层原理
            "com.wjz.nestedscroll.nestedscroll.NestedScrollImplActivity"

            // child嵌套 布局
            , "com.wjz.nestedscroll.sticky.StickyActivity"

            // coordinator.behavior 的实现原理
            , "com.wjz.nestedscroll.coordinator.DefineBehavior.ClickMove.DefineClickMoveBehaviorActivity"

            , "com.wjz.nestedscroll.coordinator.DefineBehavior.Scroll.DefineMoveActivity"

            // 自定义behavior
            , "com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity.FABScrollCoordinatorActivity"
            ,"com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity.FABBackTopActivity"
            ,"com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity.FabDownShowAddActivity"

            // 官方提供的behavior
            , "com.wjz.nestedscroll.coordinator.CollapsingToolbarLayout.CollapsingToolbarCoordinatorActivity"
            , "com.wjz.nestedscroll.coordinator.CollapsingToolbarLayout.CollapsingToolbarWithImageActivity"
            , "com.wjz.nestedscroll.coordinator.SwipeDismissBehavior.SwipeDismissBehaviorActivity"
            , "com.wjz.nestedscroll.coordinator.BottomSheetBehavior.BottomSheetBehaviorActivity"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRv = (RecyclerView) findViewById(R.id.rv_main);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        MainAdapter adapter = new MainAdapter(R.layout.main_item, Arrays.asList(label));
        mRv.setAdapter(adapter);
        mRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, label[i]);
                startActivity(intent);

            }
        });
    }
}
