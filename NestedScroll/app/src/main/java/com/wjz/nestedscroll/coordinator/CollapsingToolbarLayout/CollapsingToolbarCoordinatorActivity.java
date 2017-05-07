package com.wjz.nestedscroll.coordinator.CollapsingToolbarLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.wjz.nestedscroll.R;

/**
 * 项目名称：uiwiki
 * 包名：com.wjz.www.uiwiki.coordinator
 * 创建人：Wjz
 * 创建时间：2016/10/3
 * 类描述：
 */

public class CollapsingToolbarCoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.collapsingtoolbar_coordinator_activity);
        // 处理toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 点击返回键按钮的事件
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
