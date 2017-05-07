package com.wjz.nestedscroll.sticky;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjz.nestedscroll.R;

import java.util.ArrayList;
import java.util.List;

public class StickyTabFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sticky_tab_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 50; i++) {
            mDatas.add(mTitle + " -> " + i);
        }
        mRecyclerView.setAdapter(new BaseQuickAdapter<String>(android.R.layout.test_list_item,mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String string) {
                baseViewHolder.setText(android.R.id.text1, string);
            }
        });


        return view;
    }

    public static StickyTabFragment newInstance(String title) {
        StickyTabFragment tabFragment = new StickyTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}