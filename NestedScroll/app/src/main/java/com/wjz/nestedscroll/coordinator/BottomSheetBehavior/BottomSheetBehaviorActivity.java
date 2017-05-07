package com.wjz.nestedscroll.coordinator.BottomSheetBehavior;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wjz.nestedscroll.R;


public class BottomSheetBehaviorActivity extends AppCompatActivity {
    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_behavior_activity);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
    }

    public void doclick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                // xml
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.button1:
                // BottomSheetDialog
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.show();


                // 从BottomSheetDialog 中查找对应的design_bottom_sheet id
                // 多态 support包下的 id 过于麻烦
                View dialog = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(dialog);

                /*
                BottomSheetBehavior<View> from = BottomSheetBehavior.from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet));
                System.out.println("state:"+from.getState());*/

                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        System.out.println("newState:" + newState);
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {

                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });

                break;
            case R.id.button2:
                // DefineBottomSheetDialogFragment
                new DefineBottomSheetDialogFragment().show(getSupportFragmentManager(), "dialog");
                break;
        }
    }
}
