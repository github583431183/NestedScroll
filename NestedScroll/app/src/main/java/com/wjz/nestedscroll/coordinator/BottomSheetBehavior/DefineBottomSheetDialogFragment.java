package com.wjz.nestedscroll.coordinator.BottomSheetBehavior;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import com.wjz.nestedscroll.R;


public class DefineBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_dialog_fragment, null);
        dialog.setContentView(view);
        // 查找 behavior
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }

    public void doclick(View v) {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
