package com.wjz.nestedscroll.coordinator.CollapsingToolbarLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wjz.nestedscroll.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：uiwiki
 * 包名：com.wjz.www.uiwiki.coordinator
 * 创建人：Wjz
 * 创建时间：2016/10/3
 * 类描述：
 */

public class CollapsingToolbarWithImageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsingtoolbar_with_image_activity);
        // 处理rv
        initDate();

        // 处理toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 处理CollapsingToolbarLayout
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    }

    private void initDate() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(this, datas));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Toast.makeText(this, "点击了item1", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Integer> datas;
        private Context context;
        private List<Integer> lists;

        public MyRecyclerViewAdapter(Context context, List<Integer> datas) {
            this.datas = datas;
            this.context = context;
            getRandomHeights(datas);
        }

        private void getRandomHeights(List<Integer> datas) {
            lists = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                lists.add((int) (200 + Math.random() * 400));
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View         view       = LayoutInflater.from(context).inflate(R.layout.collapsing_image_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = lists.get(position);//把随机的高度赋予item布局
            holder.itemView.setLayoutParams(params);
            holder.mTextView.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

}
