package com.wjz.nestedscroll.coordinator.DefineBehavior.ClickMove;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wjz.nestedscroll.R;

/**
 * Created by Wjz on 2017/5/7.
 */

public class DefineClickMoveBehaviorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.define_click_move_behavior);

        TextView dependency = (TextView) findViewById(R.id.dependency);

        dependency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 每点击一次 向下移动50px
                * dependency 移动 child 移动
                * */
                ViewCompat.offsetTopAndBottom(v, 50);
            }
        });
    }
}
