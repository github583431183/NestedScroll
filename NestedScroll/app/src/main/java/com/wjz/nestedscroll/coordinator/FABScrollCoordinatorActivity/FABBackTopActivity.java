package com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjz.nestedscroll.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wjz on 2017/5/7.
 */

public class FABBackTopActivity extends AppCompatActivity {

    private FloatingActionButton FAB;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private boolean isInitializeFAB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_back_top_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("我是第" + i + "个");
        }

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        recyclerView.setAdapter(new BaseQuickAdapter<String>(android.R.layout.test_list_item,list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(android.R.id.text1,s);
            }
        });

        FAB = (FloatingActionButton) findViewById(R.id.fab);
        //回到顶部，隐藏浮动按钮
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 回到顶部
                linearLayoutManager.scrollToPosition(0);
                hideFAB();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInitializeFAB) {
            isInitializeFAB = true;
            // 回到顶部后隐藏fab
            hideFAB();
        }
    }

    private void hideFAB() {
        FAB.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorUtil.scaleHide(FAB, new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        FAB.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                    }
                });
            }
        }, 500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
