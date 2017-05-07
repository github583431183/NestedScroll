package com.wjz.nestedscroll;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 项目名称：uiwiki
 * 包名：com.wjz.www.uiwiki
 * 创建人：Wjz
 * 创建时间：2016/10/2
 * 类描述：
 */

public class MainAdapter extends BaseQuickAdapter<String> {
    public MainAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        String[] split = s.split("\\.");
        baseViewHolder.setText(R.id.tv, split[split.length-1]);
    }
}
