package com.wjz.nestedscroll.sticky;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.wjz.nestedscroll.R;

/**
 * Created by Wjz on 2017/5/7.
 */

public class StickyActivity extends AppCompatActivity {
    private String[] mTitles = new String[]{"简介", "评价", "相关"};
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private StickyTabFragment[] mFragments = new StickyTabFragment[mTitles.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stick_nested_scroll_acitivity);

        initViews();
        initDatas();
        initEvents();
    }

    private void initViews() {
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.indicator_view);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initDatas() {
        mIndicator.setTitles(mTitles);

        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = StickyTabFragment.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
    }

    private void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
