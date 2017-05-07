package com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjz.nestedscroll.R;
import com.wjz.nestedscroll.coordinator.FABScrollCoordinatorActivity.Behavior.ScaleDownShowBehavior;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：Wjz
 * 创建时间：2016/9/6
 * 类描述：
 */
public class FabDownShowAddActivity extends AppCompatActivity {

    private FloatingActionButton FAB;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private BottomSheetBehavior mBottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_down_show_add_activity);

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
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(FAB, "点宝宝干啥", Snackbar.LENGTH_SHORT).show();
            }
        });

        ScaleDownShowBehavior scaleDownShowBehavior = ScaleDownShowBehavior.from(FAB);
        scaleDownShowBehavior.setOnStateChangedListener(onStateChangedListener);

        // 获取BottomSheetBehavior的behavior
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));

    }


    /**
     * ScaleDownShowBehavior自定义的显示/隐藏接口
     *
     * 当fab显示 bottom 展开
     * 隐藏 消失
     */
    private ScaleDownShowBehavior.OnStateChangedListener
            onStateChangedListener = new ScaleDownShowBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {
            System.out.println("isShow:"+isShow);
            // 此处要操作BottomSheetBehavior
            boolean b = mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED;
            System.out.println("b:"+b);
            mBottomSheetBehavior.setState(
                    isShow ?
                    BottomSheetBehavior.STATE_EXPANDED :
                    BottomSheetBehavior.STATE_COLLAPSED);//缩放
        }
    };

    private boolean initialize = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!initialize) {
            initialize = true;
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
